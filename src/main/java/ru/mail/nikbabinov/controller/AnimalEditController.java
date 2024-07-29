package ru.mail.nikbabinov.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.mail.nikbabinov.fauna.Animal;

public class AnimalEditController {

    private Stage dialogStage;

    private TableView<Animal> tableAnimals;

    private Animal animal;

    private StartSceneViewController controllerStartScene;


    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @FXML
    private TextField ageTextField;

    @FXML
    private TextField maxNumbOfSpeciesInOneCellTextField;


    @FXML
    private TextField speedTextField;

    @FXML
    private TextField startNumbOfSpeciesInOneCellTextField;

    @FXML
    private TextField weightFoodRemoveHungerTextField;

    @FXML
    private TextField weightTextField;

    @FXML
    private void initialize() {
    }

    public void setAnimalProperty(TableView<Animal> tableAnimals) {
        this.animal = tableAnimals.getSelectionModel().getSelectedItem();
        this.tableAnimals = tableAnimals;
        ageTextField.setText(String.valueOf(animal.getAge()));
        weightTextField.setText(String.valueOf(animal.getWeight()));
        speedTextField.setText(String.valueOf(animal.getSpeed()));
        weightFoodRemoveHungerTextField.setText(String.valueOf(animal.getWeightFoodRemoveHunger()));
        maxNumbOfSpeciesInOneCellTextField.setText(String.valueOf(animal.getMaxNumbOfSpeciesInOneCell()));
        startNumbOfSpeciesInOneCellTextField.setText(String.valueOf(animal.getStartNumbOfSpeciesInOneCell()));
    }

    public void setDialogStage(Stage dialogStage, StartSceneViewController controllerStartScene) {
        this.dialogStage = dialogStage;
        this.controllerStartScene = controllerStartScene;
    }

    @FXML
    public void closeDialog() {
        dialogStage.close();
    }

    @FXML
    public void saveAnimalProperty() {
        int age = Integer.parseInt(ageTextField.getText());
        double weight = Double.parseDouble(weightTextField.getText());
        int speed = Integer.parseInt(speedTextField.getText());
        double weightFoodRemoveHunger = Double.parseDouble(weightFoodRemoveHungerTextField.getText());
        int startNumbOfSpeciesInOneCell = Integer.parseInt(startNumbOfSpeciesInOneCellTextField.getText());
        int maxNumbOfSpeciesInOneCell = Integer.parseInt(maxNumbOfSpeciesInOneCellTextField.getText());

        this.animal.setAge(age);
        this.animal.setWeight(weight);
        this.animal.setSpeed(speed);
        this.animal.setWeightFoodRemoveHunger(weightFoodRemoveHunger);
        this.animal.setStartNumbOfSpeciesInOneCell(startNumbOfSpeciesInOneCell);
        this.animal.setMaxNumbOfSpeciesInOneCell(maxNumbOfSpeciesInOneCell);
        controllerStartScene.showAnimalProperty(animal);
        dialogStage.close();
    }

}
