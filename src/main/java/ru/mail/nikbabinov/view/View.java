package ru.mail.nikbabinov.view;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.mail.nikbabinov.constants.ViewProperty;
import ru.mail.nikbabinov.constants.ViewTextConstants;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class View extends Application {

    public void applicationRun() {
        Application.launch();
    }

    @Override
    public void start(Stage startStage) throws Exception {
        setSizeStartStage(startStage);
        setTitleStartStage(startStage);
        setIconStartStage(startStage);
        setSceneStartStage(startStage);
        startStage.show();
    }

    private void setSizeStartStage(Stage StartStage) {
        StartStage.setWidth(ViewProperty.WIDTH_START_STAGE);
        StartStage.setHeight(ViewProperty.HEIGHT_START_STAGE);
        StartStage.setResizable(false);
    }

    private void setTitleStartStage(Stage StartStage) {
        StartStage.setTitle(ViewTextConstants.TITLE_START_STAGE);
    }

    private void setIconStartStage(Stage StartStage) {
        InputStream inIcon = getClass().getResourceAsStream("/images/wild_island_icons.png");
        Image icon = new Image(inIcon);
        StartStage.getIcons().add(icon);
    }

    private void setSceneStartStage(Stage StartStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL url = getClass().getResource("/fxml/startScene.fxml");
        loader.setLocation(url);
        Parent root = loader.load();
        StartStage.setScene(new Scene(root));
    }
}
