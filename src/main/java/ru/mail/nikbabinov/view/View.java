package ru.mail.nikbabinov.view;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import ru.mail.nikbabinov.app.MainApplication;
import ru.mail.nikbabinov.constants.ViewText;
import ru.mail.nikbabinov.controller.AnimalEditController;
import ru.mail.nikbabinov.controller.StartSceneViewController;
import ru.mail.nikbabinov.entity.ConfigApplication;
import ru.mail.nikbabinov.fauna.Animal;

import java.io.IOException;
import java.io.InputStream;

public class View extends Application {
    private static final MainApplication mainApplication = new MainApplication();
    private AnchorPane rootLayout;
    private Stage startStage;
    private StartSceneViewController controllerStartScene;

    public void applicationRun() {
        Application.launch();
    }

    @Override
    public void start(Stage startStage) throws Exception {
        this.startStage = startStage;
        setSizeStartStage(startStage);
        setTitleStartStage(startStage);
        setIconStartStage(startStage);
        setSceneStartStage(startStage);
    }

    private void setSizeStartStage(Stage StartStage) {
        StartStage.setWidth(ConfigApplication.getSizeWindow("width"));
        StartStage.setHeight(ConfigApplication.getSizeWindow("height"));
        StartStage.setResizable(false);
    }

    private void setTitleStartStage(Stage StartStage) {
        StartStage.setTitle(ViewText.TITLE_START_STAGE.getViewText());
    }

    private void setIconStartStage(Stage StartStage) {
        InputStream inIcon = getClass().getResourceAsStream("/images/wild_island_icons.png");
        assert inIcon != null;
        Image icon = new Image(inIcon);
        StartStage.getIcons().add(icon);
    }

    private void setSceneStartStage(Stage startStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/startScene.fxml"));
        rootLayout = (AnchorPane) loader.load();
        StartSceneViewController controllerStartScene = loader.getController();
        this.controllerStartScene = controllerStartScene;
        controllerStartScene.setApplication(this);
        startStage.setScene(new Scene(rootLayout));
        startStage.show();
    }


    public ObservableList<Animal> getObservableListAnimal() {
        return mainApplication.getObservableListAnimal();
    }

    private void showAnimalEditDialog(TableView<Animal> animalTableView){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/AnimalEditProperty.fxml"));
        AnchorPane page = null;
        try {
            page = (AnchorPane) loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage dialogStage = new Stage();
        dialogStage.setResizable(false);
        dialogStage.setTitle(ViewText.TITLE_EDIT_ANIMAL_DIALOG.getViewText());
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(startStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        AnimalEditController controller = loader.getController();
        controller.setDialogStage(dialogStage,controllerStartScene);
        controller.setAnimalProperty(animalTableView);
        dialogStage.showAndWait();
    }

    public void showEditAnimalButtons(TableView<Animal> tableAnimals) {
        Button dellAnimal = new Button("Удалить");
        Button editAnimal = new Button("Изменить");
        rootLayout.getChildren().add(dellAnimal);
        rootLayout.getChildren().add(editAnimal);
        dellAnimal.getStyleClass().add("buttonDellAnimal");
        editAnimal.getStyleClass().add("buttonEditAnimal");

        dellAnimal.setOnAction(event -> {
            mainApplication.removeAnimalInList(tableAnimals);
        });

        editAnimal.setOnAction(event -> {
            showAnimalEditDialog(tableAnimals);
        });

    }
}
