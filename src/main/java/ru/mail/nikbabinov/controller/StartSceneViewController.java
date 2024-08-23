package ru.mail.nikbabinov.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.mail.nikbabinov.entity.wildLife.WildLife;
import ru.mail.nikbabinov.entity.wildLife.fauna.Animal;
import ru.mail.nikbabinov.entity.wildLife.flora.Plant;
import ru.mail.nikbabinov.view.View;

import java.io.IOException;

public class StartSceneViewController {
    private ObservableList<WildLife> wildLifeIsland;

    private View view;

    @FXML
    private Label ageLabel;

    @FXML
    private Label maxNumbOfSpeciesInOneCellLabel;

    @FXML
    private Label speedLabel;

    @FXML
    private Label startNumbOfSpeciesInOneCellLabel;

    @FXML
    private Label weightFoodRemoveHungerLabel;

    @FXML
    private Label weightLabel;

    @FXML
    private TableView<WildLife> tableAnimals;

    @FXML
    private TableColumn<WildLife, String> tableColumnTypeAnimal;

    @FXML
    private void initialize() {
        showAnimalProperty(null);
        tableColumnTypeAnimal.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Animal) {
                return ((Animal)cellData.getValue()).nameProperty();
            }
            return ((Plant)cellData.getValue()).nameProperty();
        });
        tableAnimals.getSelectionModel().selectedItemProperty().addListener(
                (_, _, newValue) -> showAnimalProperty(newValue));
    }

    @FXML
    public void startIslandEmulation() throws IOException {
        view.startIslandEmulation(wildLifeIsland);
    }

    public void setApplication(View view) {
        this.view = view;
        this.wildLifeIsland = view.getObservableListAnimal();
        tableAnimals.setItems(wildLifeIsland);
    }


    public void showAnimalProperty(WildLife wildLifeObject) {
        if (wildLifeObject != null) {
            if (wildLifeObject instanceof Animal) {
                ageLabel.setText(String.valueOf(((Animal)wildLifeObject).getAge()));
                weightLabel.setText(String.valueOf(((Animal)wildLifeObject).getWeight()));
                speedLabel.setText(String.valueOf(((Animal)wildLifeObject).getSpeed()));
                weightFoodRemoveHungerLabel.setText(String.valueOf(((Animal)wildLifeObject).getWeightFoodRemoveHunger()));
                startNumbOfSpeciesInOneCellLabel.setText(String.valueOf(((Animal)wildLifeObject).getStartNumbOfSpeciesInOneCell()));
                maxNumbOfSpeciesInOneCellLabel.setText(String.valueOf(((Animal)wildLifeObject).getMaxNumbOfSpeciesInOneCell()));
            }
            if (wildLifeObject instanceof Plant) {
                ageLabel.setText(String.valueOf(((Plant)wildLifeObject).getAge()));
                weightLabel.setText(String.valueOf(((Plant)wildLifeObject).getWeight()));
                speedLabel.setText(String.valueOf(((Plant)wildLifeObject).getSpeed()));
                weightFoodRemoveHungerLabel.setText(String.valueOf(((Plant)wildLifeObject).getWeightFoodRemoveHunger()));
                startNumbOfSpeciesInOneCellLabel.setText(String.valueOf(((Plant)wildLifeObject).getStartNumbOfSpeciesInOneCell()));
                maxNumbOfSpeciesInOneCellLabel.setText(String.valueOf(((Plant)wildLifeObject).getMaxNumbOfSpeciesInOneCell()));
            }
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
