package ru.mail.nikbabinov.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ru.mail.nikbabinov.app.MainApplication;
import ru.mail.nikbabinov.fauna.Animal;

public class StartSceneViewController {

    private MainApplication mainApp;

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

    private final Stage startSceneStage;

    public StartSceneViewController(Stage stage) {
        this.startSceneStage = stage;
    }

    @FXML
    private void initialize() {
        showAnimalProperty(null);
        tableAnimals.setItems(this.mainApp.getObservableListAnimal());
        tableColumnTypeAnimal.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tableAnimals.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showAnimalProperty(newValue));
    }

    public void setApplication(MainApplication mainApplication) {
        this.mainApp = mainApplication;
    }


    private void showAnimalProperty(Animal animal) {
        if (animal != null) {
            ageLabel.setText(String.valueOf(animal.getAge()));
            weightLabel.setText(String.valueOf(animal.getWeight()));
            speedLabel.setText(String.valueOf(animal.getSpeed()));
            weightFoodRemoveHungerLabel.setText(String.valueOf(animal.getWeightFoodRemoveHunger()));
            startNumbOfSpeciesInOneCellLabel.setText(String.valueOf(animal.getStartNumbOfSpeciesInOneCell()));
            maxNumbOfSpeciesInOneCellLabel.setText(String.valueOf(animal.getMaxNumbOfSpeciesInOneCell()));
            showButtonDell();

        } else {
            ageLabel.setText("");
            weightLabel.setText("");
            speedLabel.setText("");
            weightFoodRemoveHungerLabel.setText("");
            startNumbOfSpeciesInOneCellLabel.setText("");
            maxNumbOfSpeciesInOneCellLabel.setText("");
        }
    }

    private void showButtonDell() {
    }
}
