package ru.mail.nikbabinov.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ru.mail.nikbabinov.app.MainApplication;
import ru.mail.nikbabinov.fauna.Animal;

public class StartSceneViewController {

    private MainApplication mainApp;
    ;

    @FXML
    private Button startButton;

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
        tableAnimals.setItems(this.mainApp.getObservableListAnimal());
        tableColumnTypeAnimal.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        startButton.setOnAction(actionEvent -> {
            System.out.println("Hello World");
        });
    }

    public void setApplication(MainApplication mainApplication) {
        this.mainApp = mainApplication;
    }
}
