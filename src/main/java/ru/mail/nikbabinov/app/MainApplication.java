package ru.mail.nikbabinov.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import ru.mail.nikbabinov.constants.ScaleViewProperty;
import ru.mail.nikbabinov.entity.ConfigApplication;
import ru.mail.nikbabinov.fauna.Animal;
import ru.mail.nikbabinov.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class MainApplication {
    public static ConcurrentHashMap<String, ObservableList<Animal>> wildIslandMap;
    private static View view;

    public static void main(String[] args) {
        view = new View();
        view.applicationRun();
    }

    public ObservableList<Animal> getObservableListAnimal() {
        return ConfigApplication.getObservableListAnimals();
    }

    public void removeAnimalInList(TableView<Animal> tableAnimals) {
        int selectedIndex = tableAnimals.getSelectionModel().getSelectedIndex();
        tableAnimals.getItems().remove(selectedIndex);
    }

    public ObservableList<Animal> createAllAnimalPopulation(ObservableList<Animal> animalObservableList) {
        ObservableList<Animal> allAnimalPopulation = FXCollections.observableArrayList();
        for (Animal animal : animalObservableList) {
            int randomNumberAnimalInOneCell = ThreadLocalRandom.current().nextInt(animal.getStartNumbOfSpeciesInOneCell(), animal.getMaxNumbOfSpeciesInOneCell() + 1);
            for (int i = 0; i < randomNumberAnimalInOneCell; i++) {
                Animal copyAnimal = copyAnimal(animal);
                allAnimalPopulation.add(copyAnimal);
            }
        }
        return allAnimalPopulation;
    }

    private Animal copyAnimal(Animal animal) {
        ObjectMapper mapper = new ObjectMapper();
        Animal copyAnimal = null;
        try {
            String animalJson = mapper.writeValueAsString(animal);
            copyAnimal = new ObjectMapper().readValue(animalJson, Animal.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return copyAnimal;
    }

    public ConcurrentHashMap<String, Map<String, Integer>> getMapAnimalWildIsland(ScaleViewProperty scaleViewProperty) {
        return getScopeStatisticalNumberAnimalsInMap(scaleViewProperty.getScale());
    }

    public void initAnimalWildIsland(ObservableList<Animal> animalObservableList) {

        ExecutorService executor = Executors.newWorkStealingPool();
        int widthIsland = ConfigApplication.getSizeIsland("width");
        int heightIsland = ConfigApplication.getSizeIsland("height");
        wildIslandMap = new ConcurrentHashMap<>();
        for (int row = 0; row < heightIsland; row++) {
            for (int column = 0; column < widthIsland; column++) {
                int finalRow = row;
                int finalColumn = column;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        wildIslandMap.put(finalRow + ":" + finalColumn, createAllAnimalPopulation(animalObservableList));
                    }
                });
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

    public ConcurrentHashMap<String, Map<String, Integer>> getStatisticalNumberAnimalsInMap() {
        ConcurrentHashMap<String, Map<String, Integer>> numberAnimalOneCellMap = new ConcurrentHashMap<>();
        for (Map.Entry<String, ObservableList<Animal>> entry : wildIslandMap.entrySet()) {
            ConcurrentHashMap<String, Integer> animalPopulation = new ConcurrentHashMap<>();
            ObservableList<Animal> animalProprieties = entry.getValue();
            String key = entry.getKey();
            for (Animal animal : animalProprieties) {
                String keyNameAnimal = animal.getClass().getSimpleName();
                if (!animalPopulation.containsKey(keyNameAnimal)) {
                    animalPopulation.put(keyNameAnimal, 1);
                } else {
                    animalPopulation.put(keyNameAnimal, animalPopulation.get(keyNameAnimal) + 1);
                }

            }
            numberAnimalOneCellMap.put(key, animalPopulation);
        }
        return numberAnimalOneCellMap;
    }

    public ConcurrentHashMap<String, Map<String, Integer>> getScopeStatisticalNumberAnimalsInMap(int scale) {

        ConcurrentHashMap<String, Map<String, Integer>> statisticalNumberAnimalsInMap = getStatisticalNumberAnimalsInMap();
        ConcurrentHashMap<String, Map<String, Integer>> scaleStatisticalNumberAnimalsInMap = new ConcurrentHashMap<>();
        for (Map.Entry<String, Map<String, Integer>> entry : statisticalNumberAnimalsInMap.entrySet()) {
            String[] keys = entry.getKey().split(":");
            Map<String, Integer> animalProprieties = entry.getValue();
            int row = Integer.parseInt(keys[0]);
            int column = Integer.parseInt(keys[1]);
            int rowScale = row / scale;
            int columnScale = column / scale;
            Map<String, Integer> scaleAnimalProprieties = getScaleAnimalProprieties(animalProprieties);
            if (!scaleStatisticalNumberAnimalsInMap.containsKey(rowScale + ":" + columnScale)) {
                scaleStatisticalNumberAnimalsInMap.put(rowScale + ":" + columnScale, scaleAnimalProprieties);
            } else {
                Map<String, Integer> scalePropertyAnimal = scaleStatisticalNumberAnimalsInMap.get(rowScale + ":" + columnScale);
                for (Map.Entry<String, Integer> entryAnimals : animalProprieties.entrySet()) {
                    String keyNameAnimal = entryAnimals.getKey();
                    int valueNumberAnimal = entryAnimals.getValue();
                    scalePropertyAnimal.put(keyNameAnimal, scalePropertyAnimal.get(keyNameAnimal) + valueNumberAnimal);
                }
            }
        }
        return scaleStatisticalNumberAnimalsInMap;
    }

    public ConcurrentHashMap<String, Map<String, Integer>> getDetailInformationOfAnimalOneCell(Integer rowIndex, Integer columnIndex, ScaleViewProperty scaleViewProperty) {
        int scale = scaleViewProperty.getScale();
        ConcurrentHashMap<String, Map<String, Integer>> statisticalNumberAnimalsInMap = getStatisticalNumberAnimalsInMap();
        ConcurrentHashMap<String, Map<String, Integer>> detailInformationOfAnimalOneCell = new ConcurrentHashMap<>();
        int row = rowIndex * scale;
        int rowScale = row + scale;
        int column = columnIndex * scale;
        int columnScale = column + scale;
        int rowInDetailInformation = 0;
        int columnInDetailInformation = 0;
        for (int rowNumber = row; rowNumber < rowScale; rowNumber++) {
            for (int columnNumber = column; columnNumber < columnScale; columnNumber++) {
                Map<String, Integer> animalsOneCell = statisticalNumberAnimalsInMap.get(rowNumber + ":" + columnNumber);
                detailInformationOfAnimalOneCell.put(rowInDetailInformation + ":" + columnInDetailInformation, animalsOneCell);
                columnInDetailInformation++;
            }
            columnInDetailInformation = 0;
            rowInDetailInformation++;
        }
        return getDetailInformationOfAnimalScaleOneTOTwoForHeight(detailInformationOfAnimalOneCell);
    }

    private ConcurrentHashMap<String, Map<String, Integer>> getDetailInformationOfAnimalScaleOneTOTwoForHeight(ConcurrentHashMap<String, Map<String, Integer>> detailInformationOfAnimalOneCell) {
        ConcurrentHashMap<String, Map<String, Integer>> scaleAnimals = new ConcurrentHashMap<>();
        for (Map.Entry<String, Map<String, Integer>> entryAnimals : detailInformationOfAnimalOneCell.entrySet()) {
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

    private static Map<String, Integer> getScaleAnimalProprieties(Map<String, Integer> animalProprieties) {
        Map<String, Integer> scaleAnimalProprieties = new HashMap<>();
        for (Map.Entry<String, Integer> entryAnimals : animalProprieties.entrySet()) {
            String keyNameAnimal = entryAnimals.getKey();
            int valueNumberAnimal = entryAnimals.getValue();
            if (!scaleAnimalProprieties.containsKey(keyNameAnimal)) {
                scaleAnimalProprieties.put(keyNameAnimal, valueNumberAnimal);
            } else {
                scaleAnimalProprieties.put(keyNameAnimal, scaleAnimalProprieties.get(keyNameAnimal) + valueNumberAnimal);
            }
        }
        return scaleAnimalProprieties;
    }
}
