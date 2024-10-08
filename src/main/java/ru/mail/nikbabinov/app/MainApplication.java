package ru.mail.nikbabinov.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import ru.mail.nikbabinov.constants.ScaleViewProperty;
import ru.mail.nikbabinov.controller.AnimalActionController;
import ru.mail.nikbabinov.controller.ConfigApplicationController;
import ru.mail.nikbabinov.entity.wildLife.WildLife;
import ru.mail.nikbabinov.entity.wildLife.fauna.*;
import ru.mail.nikbabinov.entity.wildLife.flora.Plant;
import ru.mail.nikbabinov.view.View;

import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.concurrent.*;

public class MainApplication {
    private static View view;
    private static ConcurrentHashMap<String, ObservableList<WildLife>> wildIslandMap;
    private static final int threadCount = (ManagementFactory.getThreadMXBean().getThreadCount());

    public static void main(String[] args) {
        view = new View();
        view.applicationRun();
    }

    public static ConcurrentHashMap<String, ObservableList<WildLife>> getWildIslandMap() {
        return wildIslandMap;
    }

    public ObservableList<WildLife> getObservableListAnimal() {
        return ConfigApplicationController.getObservableListAnimals();
    }

    public void removeAnimalInList(TableView<WildLife> tableAnimals) {
        int selectedIndex = tableAnimals.getSelectionModel().getSelectedIndex();
        tableAnimals.getItems().remove(selectedIndex);
    }

    public ObservableList<WildLife> createAllPopulation(ObservableList<WildLife> animalObservableList) {
        ObservableList<WildLife> allPopulationWildIsland = FXCollections.observableArrayList();
        for (WildLife populations : animalObservableList) {
            int randomNumberAnimalInOneCell = 0;
            if (populations instanceof Animal) {
                randomNumberAnimalInOneCell = ThreadLocalRandom.current().nextInt(((Animal) populations).getStartNumbOfSpeciesInOneCell(), ((Animal) populations).getMaxNumbOfSpeciesInOneCell() + 1);
            }
            if (populations instanceof Plant) {
                randomNumberAnimalInOneCell = ThreadLocalRandom.current().nextInt(((Plant) populations).getStartNumbOfSpeciesInOneCell(), ((Plant) populations).getMaxNumbOfSpeciesInOneCell() + 1);
            }
            for (int i = 0; i < randomNumberAnimalInOneCell; i++) {
                if (populations instanceof Animal) {
                    Animal copyAnimal = copyAnimal((Animal) populations);
                    allPopulationWildIsland.add(copyAnimal);
                }
                if (populations instanceof Plant) {
                    Plant copyPlant = copyPlant((Plant) populations);
                    allPopulationWildIsland.add(copyPlant);
                }
            }
        }
        return allPopulationWildIsland;
    }

    private Plant copyPlant(Plant populations) {
        ObjectMapper mapper = new ObjectMapper();
        Plant plantCopy;
        try {
            String plantJson = mapper.writeValueAsString(populations);
            plantCopy = new ObjectMapper().readValue(plantJson, Plant.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return plantCopy;
    }

    private Animal copyAnimal(Animal animal) {
        ObjectMapper mapper = new ObjectMapper();
        Animal copyObject;
        try {
            String animalJson = mapper.writeValueAsString(animal);
            copyObject = new ObjectMapper().readValue(animalJson, Animal.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return copyObject;
    }

    public ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> getMapAnimalWildIsland(ScaleViewProperty scaleViewProperty) {
        return getScopeStatisticalNumberAnimalsInMap(scaleViewProperty.getScale());
    }

    public void initAnimalWildIsland(ObservableList<WildLife> animalObservableList) {

        ExecutorService executor = Executors.newWorkStealingPool(threadCount);
        int widthIsland = ConfigApplicationController.getSizeIsland("width");
        int heightIsland = ConfigApplicationController.getSizeIsland("height");
        wildIslandMap = new ConcurrentHashMap<>();
        for (int row = 0; row < heightIsland; row++) {
            for (int column = 0; column < widthIsland; column++) {
                int finalRow = row;
                int finalColumn = column;
                executor.execute(() -> wildIslandMap.put(finalRow + ":" + finalColumn, createAllPopulation(animalObservableList)));
            }
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> getStatisticalNumberAnimalsInMap() {
        ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> numberAnimalOneCellMap = new ConcurrentHashMap<>();
        for (Map.Entry<String, ObservableList<WildLife>> entry : wildIslandMap.entrySet()) {
            ConcurrentHashMap<String, Integer> animalPopulation = getStringIntegerConcurrentHashMap(entry);
            String key = entry.getKey();
            numberAnimalOneCellMap.put(key, animalPopulation);
        }
        return numberAnimalOneCellMap;
    }

    private static ConcurrentHashMap<String, Integer> getStringIntegerConcurrentHashMap(Map.Entry<String, ObservableList<WildLife>> entry) {
        synchronized (wildIslandMap) {
            ObservableList<WildLife> wildLifeProperty = entry.getValue();
            ConcurrentHashMap<String, Integer> animalPopulation = new ConcurrentHashMap<>();
            for (WildLife wildLifeObject : wildLifeProperty) {
                String keyNameAnimal = wildLifeObject.getClass().getSimpleName();
                if (!animalPopulation.containsKey(keyNameAnimal)) animalPopulation.put(keyNameAnimal, 1);
                else animalPopulation.put(keyNameAnimal, animalPopulation.get(keyNameAnimal) + 1);
            }

            return animalPopulation;
        }
    }

    public ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> getScopeStatisticalNumberAnimalsInMap(int scale) {
        ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> statisticalNumberAnimalsInMap = getStatisticalNumberAnimalsInMap();
        ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> scaleStatisticalNumberAnimalsInMap = new ConcurrentHashMap<>();
        for (Map.Entry<String, ConcurrentHashMap<String, Integer>> entry : statisticalNumberAnimalsInMap.entrySet()) {
            ConcurrentHashMap<String, Integer> animalProprieties = entry.getValue();
            String[] keys = entry.getKey().split(":");
            int row = Integer.parseInt(keys[0]);
            int column = Integer.parseInt(keys[1]);
            int rowScale = row / scale;
            int columnScale = column / scale;

            if (!scaleStatisticalNumberAnimalsInMap.containsKey(rowScale + ":" + columnScale)) {
                scaleStatisticalNumberAnimalsInMap.put(rowScale + ":" + columnScale, animalProprieties);
            } else {
                ConcurrentHashMap<String, Integer> animalInScaleMapOneCell = scaleStatisticalNumberAnimalsInMap.get(rowScale + ":" + columnScale);
                for (Map.Entry<String, Integer> animal : animalInScaleMapOneCell.entrySet()) {
                    String keyGetAnimal = animal.getKey();
                    int numberAnimalOneCell = animal.getValue();
                    for (Map.Entry<String, Integer> animalEntry : animalProprieties.entrySet()) {
                        String keyEntryAnimal = animalEntry.getKey();
                        int valueEntryAnimal = animalEntry.getValue();
                        if (keyEntryAnimal.equalsIgnoreCase(keyGetAnimal)) {
                            int sumAnimalInOneCell = numberAnimalOneCell + valueEntryAnimal;
                            scaleStatisticalNumberAnimalsInMap.get(rowScale + ":" + columnScale).put(keyGetAnimal, sumAnimalInOneCell);
                        }

                    }
                }
            }
        }
        return scaleStatisticalNumberAnimalsInMap;
    }

    public ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> getDetailInformationOfAnimalOneCell(Integer rowIndex, Integer columnIndex, ScaleViewProperty scaleViewProperty) {
        int scale = scaleViewProperty.getScale();
        ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> statisticalNumberAnimalsInMap = getStatisticalNumberAnimalsInMap();
        ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> detailInformationOfAnimalOneCell = new ConcurrentHashMap<>();
        int row = rowIndex * scale;
        int rowScale = row + scale;
        int column = columnIndex * scale;
        int columnScale = column + scale;
        int rowInDetailInformation = 0;
        int columnInDetailInformation = 0;
        for (int rowNumber = row; rowNumber < rowScale; rowNumber++) {
            for (int columnNumber = column; columnNumber < columnScale; columnNumber++) {
                ConcurrentHashMap<String, Integer> animalsOneCell = statisticalNumberAnimalsInMap.get(rowNumber + ":" + columnNumber);
                detailInformationOfAnimalOneCell.put(rowInDetailInformation + ":" + columnInDetailInformation, animalsOneCell);
                columnInDetailInformation++;
            }
            columnInDetailInformation = 0;
            rowInDetailInformation++;
        }
        return getDetailInformationOfAnimalScaleOneTOTwoForHeight(detailInformationOfAnimalOneCell);
    }

    private ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> getDetailInformationOfAnimalScaleOneTOTwoForHeight(ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> detailInformationOfAnimalOneCell) {
        ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> scaleAnimals = new ConcurrentHashMap<>();
        for (ConcurrentHashMap.Entry<String, ConcurrentHashMap<String, Integer>> entryAnimals : detailInformationOfAnimalOneCell.entrySet()) {
            String[] keys = entryAnimals.getKey().split(":");
            int rowAnimals = Integer.parseInt(keys[0]);
            int columnAnimals = Integer.parseInt(keys[1]);
            int rowAnimalsScale = (rowAnimals >= 5) ? 1 : 0;
            Map<String, Integer> oneAnimal = entryAnimals.getValue();
            if (!scaleAnimals.containsKey(rowAnimalsScale + ":" + columnAnimals)) {
                scaleAnimals.put(rowAnimalsScale + ":" + columnAnimals, entryAnimals.getValue());
            } else {
                Map<String, Integer> oneCellProperty = scaleAnimals.get(rowAnimalsScale + ":" + columnAnimals);
                for (Map.Entry<String, Integer> entry : oneCellProperty.entrySet()) {
                    for (Map.Entry<String, Integer> oneCell : oneAnimal.entrySet()) {
                        if (entry.getKey().equals(oneCell.getKey())) {
                            oneCellProperty.put(oneCell.getKey(), oneCell.getValue() + entry.getValue());
                        }
                    }
                }
            }

        }
        return scaleAnimals;
    }

    public void runLifeAnimal(GridPane gridPaneFieldAnimal) {
        ScheduledExecutorService executorField = Executors.newScheduledThreadPool(2);

        executorField.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        view.fillGridPane(gridPaneFieldAnimal, getMapAnimalWildIsland(ScaleViewProperty.MAIN_WILD_ISLAND_STAGE));
                    }
                });

            }
        }, 0, 200, TimeUnit.MILLISECONDS);


        ExecutorService executorService = Executors.newFixedThreadPool(threadCount / 2);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        for (Map.Entry<String, ObservableList<WildLife>> animalsInCelField : wildIslandMap.entrySet())
            synchronized (wildIslandMap) {
                scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                AnimalActionController.animalMove();
                            }
                        });
                    }
                }, 5, 100, TimeUnit.MILLISECONDS);
            }
    }

}
