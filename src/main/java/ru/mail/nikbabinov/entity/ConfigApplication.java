package ru.mail.nikbabinov.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.mail.nikbabinov.constants.PathToFile;
import ru.mail.nikbabinov.fauna.Animal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConfigApplication {
    private static final ObservableList<Animal> animals = FXCollections.observableArrayList();

    public static int getSizeWindow(String widthOrHeight) {
        String configFile = ConfigApplication.getConfigFile();
        String[] view = configFile.substring(configFile.indexOf("<view>") + 7, configFile.indexOf("</view>")).split("\\|");
        int width = Integer.parseInt((view[0].substring(view[0].indexOf(":") + 1).trim()));
        int height = Integer.parseInt((view[1].substring(view[1].indexOf(":") + 1).trim()));
        return switch (widthOrHeight) {
            case "width" -> width;
            case "height" -> height;
            default -> 0;
        };
    }

    public static ObservableList<Animal> getObservableListAnimals() {
        String configApplication = ConfigApplication.getConfigFile();
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
