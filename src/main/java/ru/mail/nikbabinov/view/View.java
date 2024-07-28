package ru.mail.nikbabinov.view;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import ru.mail.nikbabinov.app.MainApplication;
import ru.mail.nikbabinov.constants.ViewText;
import ru.mail.nikbabinov.controller.StartSceneViewController;
import ru.mail.nikbabinov.entity.ConfigApplication;
import ru.mail.nikbabinov.fauna.Animal;

import java.io.IOException;
import java.io.InputStream;

public class View extends Application {
    private static final MainApplication mainApplication = MainApplication.getInstance();
    private AnchorPane rootLayout;

    public void applicationRun() {
        Application.launch();
    }

    @Override
    public void start(Stage startStage) throws Exception {
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
        StartSceneViewController controller = loader.getController();
        controller.setApplication(this);
        startStage.setScene(new Scene(rootLayout));
        startStage.show();
    }


    public ObservableList<Animal> getObservableListAnimal() {
        return mainApplication.getObservableListAnimal();
    }
}
