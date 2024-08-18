package ru.mail.nikbabinov.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import ru.mail.nikbabinov.app.MainApplication;
import ru.mail.nikbabinov.constants.ImageUnicodeAnimal;
import ru.mail.nikbabinov.constants.ScaleViewProperty;
import ru.mail.nikbabinov.constants.ViewText;
import ru.mail.nikbabinov.controller.AnimalEditController;
import ru.mail.nikbabinov.controller.StartSceneViewController;
import ru.mail.nikbabinov.controller.WildIslandController;
import ru.mail.nikbabinov.entity.ConfigApplication;
import ru.mail.nikbabinov.fauna.Animal;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class View extends Application {
    private static final MainApplication mainApplication = new MainApplication();
    private AnchorPane rootLayout;
    private Stage startStage;
    private Stage wildIslandStage;
    private Stage awaitStage;
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

    public void showWildIsland(ConcurrentHashMap<String, Map<String, Integer>> mapAnimalWildIsland) throws IOException {
        wildIslandStage = new Stage();
        setSizeStartStage(wildIslandStage);
        setTitleStartStage(wildIslandStage);
        setIconStartStage(wildIslandStage);
        awaitStage.close();
        setSceneWildIsland(wildIslandStage, mapAnimalWildIsland);
    }

    private void showAnimalEditDialog(TableView<Animal> animalTableView) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/AnimalEditProperty.fxml"));
        AnchorPane page;
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
        controller.setDialogStage(dialogStage, controllerStartScene);
        controller.setAnimalProperty(animalTableView);
        setIconStartStage(dialogStage);
        dialogStage.showAndWait();
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

    private void setSceneWildIsland(Stage wildIslandStage, ConcurrentHashMap<String, Map<String, Integer>> mapAnimalWildIsland) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/wildIsland.fxml"));
        BorderPane page = (BorderPane) loader.load();
        GridPane gridPane = createGridPane(mapAnimalWildIsland);
        fillGridPane(gridPane, mapAnimalWildIsland);
        addEventHandlerToGridPane(gridPane);
        page.setCenter(gridPane);
        WildIslandController controller = loader.getController();
        wildIslandStage.setScene(new Scene(page));
        wildIslandStage.show();
        startStage.close();
    }

    private void addEventHandlerToGridPane(GridPane gridPane) {
        ObservableList<Node> childrenGridPane = gridPane.getChildrenUnmodifiable();
        for (Node node : childrenGridPane) {
            if (node instanceof Pane pane) {
                pane.setOnMouseClicked(event -> {
                    System.out.printf("Mouse enetered cell [%d, %d]%n", (GridPane.getRowIndex(node)), GridPane.getColumnIndex(node));
                });
            }
        }
    }

    private void fillGridPane(GridPane gridPane, ConcurrentHashMap<String, Map<String, Integer>> mapAnimalWildIsland) {
        ObservableList<Node> childrenGridPane = gridPane.getChildrenUnmodifiable();
        for (Node node : childrenGridPane) {
            if (node instanceof Pane pane) {
                Map<String, Integer> animals = mapAnimalWildIsland.get((GridPane.getRowIndex(node)) + ":" + GridPane.getColumnIndex(node));
                List<Node> label = pane.getChildren().stream().filter(c -> c instanceof Label).toList();
                List<Label> labelImageUnicodeAnimal = getLabelImageUnicodeAnimal(label);
                List<Label> labelStatisticalTotalNumberAnimal = getLabelStatisticalTotalNumberAnimal(label);
                int iterator = 0;
                for (Map.Entry<String, Integer> animal : animals.entrySet()) {
                    String typeAnimal = animal.getKey();
                    labelImageUnicodeAnimal.get(iterator).getStyleClass().add(typeAnimal);
                    labelImageUnicodeAnimal.get(iterator).getStyleClass().add("Animals");
                    labelStatisticalTotalNumberAnimal.get(iterator).getStyleClass().add("LabelStatistical");
                    String imageUnicode = ImageUnicodeAnimal.valueOf(typeAnimal.toUpperCase()).getImage();
                    (labelImageUnicodeAnimal.get(iterator)).setText(imageUnicode);
                    labelStatisticalTotalNumberAnimal.get(iterator).setText(String.valueOf(animal.getValue()));
                    iterator++;
                }
            }
        }
    }


    private List<Label> getLabelStatisticalTotalNumberAnimal(List<Node> label) {
        ArrayList<Label> labelStatisticalTotalNumberAnimal = new ArrayList<>();
        for (Node node : label) {
            if (node.idProperty().getValue().equals("statisticalTotalNumberAnimal")) {
                labelStatisticalTotalNumberAnimal.add((Label) node);
            }
        }
        return labelStatisticalTotalNumberAnimal;
    }

    private List<Label> getLabelImageUnicodeAnimal(List<Node> label) {
        ArrayList<Label> labels = new ArrayList<>();
        for (Node node : label) {
            if (node.idProperty().getValue().equals("imageUnicodeAnimal")) {
                labels.add((Label) node);
            }
        }
        return labels;
    }

    private GridPane createGridPane(ConcurrentHashMap<String, Map<String, Integer>> mapAnimalWildIsland) {
        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("GridPane");
        int numColumns = ConfigApplication.getSizeIsland("width") / ScaleViewProperty.MAIN_WILD_ISLAND_STAGE.getScale();
        int numRows = ConfigApplication.getSizeIsland("height") / ScaleViewProperty.MAIN_WILD_ISLAND_STAGE.getScale();
        for (int i = 0; i < numColumns; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            gridPane.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        for (int currentRow = 0; currentRow < numRows; currentRow++) {
            for (int currentColumn = 0; currentColumn < numColumns; currentColumn++) {
                addLabelAnimalInGridPane(currentColumn, currentRow, gridPane, mapAnimalWildIsland.get(currentRow + ":" + currentColumn));
            }
        }
        return gridPane;
    }

    private void addLabelAnimalInGridPane(int colIndex, int rowIndex, GridPane gridPane, Map<String, Integer> animals) {
        Pane pane = new Pane();
        double fxTranslateY = 0;
        for (Map.Entry<String, Integer> _ : animals.entrySet()) {
            Label imageUnicodeAnimal = new Label();
            Label statisticalTotalNumberAnimal = new Label();
            imageUnicodeAnimal.setId("imageUnicodeAnimal");
            statisticalTotalNumberAnimal.setId("statisticalTotalNumberAnimal");
            imageUnicodeAnimal.setStyle("-fx-translate-y:" + fxTranslateY + ";");
            statisticalTotalNumberAnimal.setStyle("-fx-translate-y:" + fxTranslateY + ";");
            statisticalTotalNumberAnimal.setAlignment(Pos.CENTER);
            fxTranslateY += 16.5;
            pane.getChildren().add(imageUnicodeAnimal);
            pane.getChildren().add(statisticalTotalNumberAnimal);
        }
        pane.getStyleClass().add("Pane");
        gridPane.add(pane, colIndex, rowIndex);
    }


    public void showEditAnimalButtons(TableView<Animal> tableAnimals) {
        Button dellAnimal = new Button("Удалить");
        Button editAnimal = new Button("Изменить");
        rootLayout.getChildren().add(dellAnimal);
        rootLayout.getChildren().add(editAnimal);
        dellAnimal.getStyleClass().add("buttonDellAnimal");
        editAnimal.getStyleClass().add("buttonEditAnimal");

        dellAnimal.setOnAction(_ ->
                mainApplication.removeAnimalInList(tableAnimals)
        );

        editAnimal.setOnAction(_ ->
                showAnimalEditDialog(tableAnimals)
        );

    }

    public void startIslandEmulation(ObservableList<Animal> animals) throws IOException {
        awaitCreateAnimals();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainApplication.initAnimalWildIsland(animals);
                try {
                    showWildIsland(mainApplication.getMapAnimalWildIsland(ScaleViewProperty.MAIN_WILD_ISLAND_STAGE));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public ObservableList<Animal> getObservableListAnimal() {
        return mainApplication.getObservableListAnimal();
    }

    public void awaitCreateAnimals() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/awaitLoadStageWildIsland.fxml"));
        AnchorPane awaitPage;
        try {
            awaitPage = (AnchorPane) loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        awaitStage = new Stage();
        awaitStage.setResizable(false);
        awaitStage.initModality(Modality.WINDOW_MODAL);
        awaitStage.initOwner(wildIslandStage);
        Scene scene = new Scene(awaitPage);
        awaitStage.setScene(scene);
        setIconStartStage(awaitStage);
        awaitStage.show();

    }
}
