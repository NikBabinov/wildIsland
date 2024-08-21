package ru.mail.nikbabinov.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.mail.nikbabinov.entity.fauna.Animal;

import java.util.ArrayList;
import java.util.List;

public class AnimalEditController {

    private Stage dialogStage;

    private TableView<Animal> tableAnimals;

    private Animal animal;

    private StartSceneViewController controllerStartScene;

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

        List<TextField> textFields = new ArrayList<>();
        textFields.add(ageTextField);
        textFields.add(weightTextField);
        textFields.add(speedTextField);
        textFields.add(weightFoodRemoveHungerTextField);
        textFields.add(maxNumbOfSpeciesInOneCellTextField);
        textFields.add(startNumbOfSpeciesInOneCellTextField);

        if (checkFieldIsNumber(textFields)) {
            animal.setAge(Integer.parseInt(ageTextField.getText()));
            animal.setWeight(Double.parseDouble(weightTextField.getText()));
            animal.setSpeed(Integer.parseInt(speedTextField.getText()));
            animal.setWeightFoodRemoveHunger(Double.parseDouble(weightFoodRemoveHungerTextField.getText()));
            animal.setMaxNumbOfSpeciesInOneCell(Integer.parseInt(maxNumbOfSpeciesInOneCellTextField.getText()));
            animal.setStartNumbOfSpeciesInOneCell(Integer.parseInt(startNumbOfSpeciesInOneCellTextField.getText()));
            controllerStartScene.showAnimalProperty(animal);
            dialogStage.close();
        } else {
            viewErrorField(textFields);
        }
    }

    private void viewErrorField(List<TextField> textFields) {
        for (TextField textField : textFields) {
            if (!isInteger(textField)) {
                textField.setStyle("-fx-background-color: #FF69B4");
            } else {
                textField.setStyle("-fx-background-color: white");
            }
        }
    }

    private Boolean checkFieldIsNumber(List<TextField> textFields) {
        for (TextField textField : textFields) {
            if (!isInteger(textField)) {
                return false;
            }
        }
        return true;
    }

    public Boolean isInteger(TextField textField) {
        return textField.getText().matches("^[0-9]+\\.?[0-9]*$");
    }

}
