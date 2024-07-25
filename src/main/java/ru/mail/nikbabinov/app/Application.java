package ru.mail.nikbabinov.app;

import ru.mail.nikbabinov.fauna.Animal;
import ru.mail.nikbabinov.view.View;

import java.util.List;

public class Application {

    public static void main(String[] args) {
       List<Animal> animals = ConfigApplication.getListAnimals(ConfigApplication.getConfigFile());
        View view = new View();
        view.applicationRun();
    }
}
