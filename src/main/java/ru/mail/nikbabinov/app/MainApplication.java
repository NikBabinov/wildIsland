package ru.mail.nikbabinov.app;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import ru.mail.nikbabinov.entity.ConfigApplication;
import ru.mail.nikbabinov.fauna.Animal;
import ru.mail.nikbabinov.view.View;

public class MainApplication {

    public static void main(String[] args) {
        View view = new View();
        view.applicationRun();
    }

    public ObservableList<Animal> getObservableListAnimal() {
        return ConfigApplication.getObservableListAnimals();
    }

    public void removeAnimalInList (TableView<Animal> tableAnimals){
        int selectedIndex = tableAnimals.getSelectionModel().getSelectedIndex();
        tableAnimals.getItems().remove(selectedIndex);
    }
}
