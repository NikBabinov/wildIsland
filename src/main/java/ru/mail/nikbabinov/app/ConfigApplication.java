package ru.mail.nikbabinov.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.mail.nikbabinov.constants.PathToFile;
import ru.mail.nikbabinov.fauna.Animal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigApplication {

    public static List<Animal> getListAnimals(String configApplication) {
        String[] animalList = configApplication.substring(configApplication.indexOf("<animalList>") + 13, configApplication.indexOf("</animalList>")).split("\\|");
        List<Animal> animals = new ArrayList<>();
        for (String animalString : animalList) {
            Animal animal;
            try {
                animal = new ObjectMapper().readValue(animalString, Animal.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            animals.add(animal);
        }
        for (Animal animal : animals) {
            System.out.println(animal.getClass());
        }
        return animals;
    }

    public static String getConfigFile() {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(PathToFile.CONFIG_FILE))) {
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
