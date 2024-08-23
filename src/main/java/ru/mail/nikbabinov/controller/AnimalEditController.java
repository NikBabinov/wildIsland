package ru.mail.nikbabinov.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.mail.nikbabinov.entity.wildLife.WildLife;
import ru.mail.nikbabinov.entity.wildLife.fauna.Animal;
import ru.mail.nikbabinov.entity.wildLife.flora.Plant;

import java.util.ArrayList;
import java.util.List;

public class AnimalEditController {

    private Stage dialogStage;

    private TableView<WildLife> wildLifeTableView;

    private WildLife wildLifeObject;

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

    public void setAnimalProperty(TableView<WildLife> tableAnimals) {
        this.wildLifeObject = tableAnimals.getSelectionModel().getSelectedItem();
        this.wildLifeTableView = tableAnimals;
        if (wildLifeObject instanceof Animal) {
            ageTextField.setText(String.valueOf(((Animal)wildLifeObject).getAge()));
            weightTextField.setText(String.valueOf(((Animal)wildLifeObject).getWeight()));
            speedTextField.setText(String.valueOf(((Animal)wildLifeObject).getSpeed()));
            weightFoodRemoveHungerTextField.setText(String.valueOf(((Animal)wildLifeObject).getWeightFoodRemoveHunger()));
            maxNumbOfSpeciesInOneCellTextField.setText(String.valueOf(((Animal)wildLifeObject).getMaxNumbOfSpeciesInOneCell()));
            startNumbOfSpeciesInOneCellTextField.setText(String.valueOf(((Animal)wildLifeObject).getStartNumbOfSpeciesInOneCell()));
        }
        if (wildLifeObject instanceof Plant) {
            ageTextField.setText(String.valueOf(((Plant)wildLifeObject).getAge()));
            weightTextField.setText(String.valueOf(((Plant)wildLifeObject).getWeight()));
            speedTextField.setText(String.valueOf(((Plant)wildLifeObject).getSpeed()));
            weightFoodRemoveHungerTextField.setText(String.valueOf(((Plant)wildLifeObject).getWeightFoodRemoveHunger()));
            maxNumbOfSpeciesInOneCellTextField.setText(String.valueOf(((Plant)wildLifeObject).getMaxNumbOfSpeciesInOneCell()));
            startNumbOfSpeciesInOneCellTextField.setText(String.valueOf(((Plant)wildLifeObject).getStartNumbOfSpeciesInOneCell()));
        }
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
            ((Animal)wildLifeObject).setAge(Integer.parseInt(ageTextField.getText()));
            ((Animal)wildLifeObject).setWeight(Double.parseDouble(weightTextField.getText()));
            ((Animal)wildLifeObject).setSpeed(Integer.parseInt(speedTextField.getText()));
            ((Animal)wildLifeObject).setWeightFoodRemoveHunger(Double.parseDouble(weightFoodRemoveHungerTextField.getText()));
            ((Animal)wildLifeObject).setMaxNumbOfSpeciesInOneCell(Integer.parseInt(maxNumbOfSpeciesInOneCellTextField.getText()));
            ((Animal)wildLifeObject).setStartNumbOfSpeciesInOneCell(Integer.parseInt(startNumbOfSpeciesInOneCellTextField.getText()));
            controllerStartScene.showAnimalProperty(wildLifeObject);
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
