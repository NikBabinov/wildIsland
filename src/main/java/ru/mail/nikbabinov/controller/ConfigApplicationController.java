package ru.mail.nikbabinov.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.mail.nikbabinov.constants.PathToFile;
import ru.mail.nikbabinov.entity.wildLife.WildLife;
import ru.mail.nikbabinov.entity.wildLife.fauna.Animal;
import ru.mail.nikbabinov.entity.wildLife.flora.Plant;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConfigApplicationController {
    private static final ObservableList<WildLife> WILD_LIFE = FXCollections.observableArrayList();

    public static int getSizeIsland(String widthOrHeight) {
        String configFile = ConfigApplicationController.getConfigFile();
        String[] field = configFile.substring(configFile.indexOf("<sizeField>") + 10, configFile.indexOf("</sizeField>")).split("\\|");
        return getWidthOrHeight(field, widthOrHeight);
    }

    public static int getSizeWindow(String widthOrHeight) {
        String configFile = ConfigApplicationController.getConfigFile();
        String[] view = configFile.substring(configFile.indexOf("<view>") + 7, configFile.indexOf("</view>")).split("\\|");
        return getWidthOrHeight(view, widthOrHeight);
    }

    private static int getWidthOrHeight(String[] widthAndHeight, String widthOrHeightName) {
        return switch (widthOrHeightName) {
            case "width" -> Integer.parseInt((widthAndHeight[0].substring(widthAndHeight[0].indexOf(":") + 1).trim()));
            case "height" -> Integer.parseInt((widthAndHeight[1].substring(widthAndHeight[1].indexOf(":") + 1).trim()));
            default -> 0;
        };
    }

    public static ObservableList<WildLife> getObservableListAnimals() {
        String configApplication = ConfigApplicationController.getConfigFile();
        String[] animalAndPlantList = configApplication.substring(configApplication.indexOf("<animalList>") + 13, configApplication.indexOf("</animalList>")).split("\\|");
        for (String animalOrPlant : animalAndPlantList) {
            if (animalOrPlant.contains("\"relation\" : \"Animal\"")) {
                Animal animal;
                try {
                    animal = new ObjectMapper().readValue(animalOrPlant, Animal.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                WILD_LIFE.add(animal);
            }
            if (animalOrPlant.contains("\"relation\" : \"Plant\"")) {
                Plant plant;
                try {
                    plant = new ObjectMapper().readValue(animalOrPlant, Plant.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                WILD_LIFE.add(plant);
            }

        }
        return WILD_LIFE;
    }

    public static String getConfigFile() {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(PathToFile.CONFIG_FILE.getPath()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
                result.append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result.toString();
    }
}
