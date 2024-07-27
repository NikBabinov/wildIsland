package ru.mail.nikbabinov.view;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import ru.mail.nikbabinov.app.MainApplication;
import ru.mail.nikbabinov.constants.ViewText;
import ru.mail.nikbabinov.controller.StartSceneViewController;
import ru.mail.nikbabinov.entity.ConfigApplication;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class View extends Application {
    private Stage startStage;

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
        StartSceneViewController controller = new StartSceneViewController(startStage);
        FXMLLoader loader = new FXMLLoader();
        URL url = getClass().getResource("/fxml/startScene.fxml");
        loader.setLocation(url);
        loader.setController(controller);
        controller.setApplication(MainApplication.getInstance());
        Parent root = loader.load();
        startStage.setScene(new Scene(root));
        startStage.show();
    }

    public Stage getStartStage() {
        return startStage;
    }
}
