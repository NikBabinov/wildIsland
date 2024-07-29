package ru.mail.nikbabinov.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.mail.nikbabinov.fauna.Animal;
import ru.mail.nikbabinov.view.View;

public class StartSceneViewController {

    private View view;

    @FXML
    private Label ageLabel;

    @FXML
    private Label maxNumbOfSpeciesInOneCellLabel;

    @FXML
    private Label speedLabel;

    @FXML
    private SplitPane splitPane;

    @FXML
    private Button startButton;

    @FXML
    private Label startNumbOfSpeciesInOneCellLabel;

    @FXML
    private Label weightFoodRemoveHungerLabel;

    @FXML
    private Label weightLabel;

    @FXML
    private TableView<Animal> tableAnimals;

    @FXML
    private TableColumn<Animal, String> tableColumnTypeAnimal;

    @FXML
    private void initialize() {
        showAnimalProperty(null);
        tableColumnTypeAnimal.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tableAnimals.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showAnimalProperty(newValue));
    }

    public void setApplication(View view) {
        this.view = view;
        tableAnimals.setItems(view.getObservableListAnimal());
    }


    public void showAnimalProperty(Animal animal) {
        if (animal != null) {
            ageLabel.setText(String.valueOf(animal.getAge()));
            weightLabel.setText(String.valueOf(animal.getWeight()));
            speedLabel.setText(String.valueOf(animal.getSpeed()));
            weightFoodRemoveHungerLabel.setText(String.valueOf(animal.getWeightFoodRemoveHunger()));
            startNumbOfSpeciesInOneCellLabel.setText(String.valueOf(animal.getStartNumbOfSpeciesInOneCell()));
            maxNumbOfSpeciesInOneCellLabel.setText(String.valueOf(animal.getMaxNumbOfSpeciesInOneCell()));
            view.showEditAnimalButtons(tableAnimals);

        } else {
            ageLabel.setText("");
            weightLabel.setText("");
            speedLabel.setText("");
            weightFoodRemoveHungerLabel.setText("");
            startNumbOfSpeciesInOneCellLabel.setText("");
            maxNumbOfSpeciesInOneCellLabel.setText("");
        }
    }
}
