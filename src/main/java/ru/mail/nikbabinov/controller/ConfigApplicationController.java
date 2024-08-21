package ru.mail.nikbabinov.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.mail.nikbabinov.constants.PathToFile;
import ru.mail.nikbabinov.entity.fauna.Animal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConfigApplicationController {
    private static final ObservableList<Animal> animals = FXCollections.observableArrayList();

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

    public static ObservableList<Animal> getObservableListAnimals() {
        String configApplication = ConfigApplicationController.getConfigFile();
        String[] animalList = configApplication.substring(configApplication.indexOf("<animalList>") + 13, configApplication.indexOf("</animalList>")).split("\\|");
        for (String animalString : animalList) {
            Animal animal;
            try {
                animal = new ObjectMapper().readValue(animalString, Animal.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            animals.add(animal);
        }
        return animals;
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
