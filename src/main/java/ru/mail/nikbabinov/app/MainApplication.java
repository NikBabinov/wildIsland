package ru.mail.nikbabinov.app;

import javafx.collections.ObservableList;
import ru.mail.nikbabinov.entity.ConfigApplication;
import ru.mail.nikbabinov.fauna.Animal;
import ru.mail.nikbabinov.view.View;

public class MainApplication {
    private static MainApplication INSTANCE = new MainApplication();


    private MainApplication() {
    }

    public static MainApplication getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainApplication();
        }
        return INSTANCE;
    }

    public static void main(String[] args) {
        View view = new View();
        view.applicationRun();
    }

    public ObservableList<Animal> getObservableListAnimal() {
        return ConfigApplication.getObservableListAnimals();
    }
}
