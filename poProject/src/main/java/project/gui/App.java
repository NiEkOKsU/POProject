package project.gui;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.*;
import java.io.FileNotFoundException;

public class App extends Application {
    private AbstractWorldMap map;
    private Vector lowerLeft;
    private Vector upperRight;
    private GridPane grid = new GridPane();
    private final VBox userArgs = new VBox(grid);

    private Stage StartSImulationStage;
    private Scene StartSimScene;
    private VBox vbox;
    private Button StartSImButton;

    private Stage SimStage;
    private Scene SimScene;

    public void init() {
        try {
            map = new Earth(10, 10, 40);
            //map.place(new Animal(map, new Vector(2,2)));
            lowerLeft = map.findLeftBottomCorner();
            upperRight = map.findRightTopCorner();
        }
        catch (IllegalArgumentException exception) {
            exception.printStackTrace();
        }
    }

    private VBox addStartButtonAndTextField() {
        TextField textField = new TextField();
        Button button = new Button("Let's get this party started!");
        button.setOnAction(actionEvent -> startEngine(textField));
        return new VBox(textField, button);
    }

    private void startEngine(TextField textField) {
        new VBox(textField);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StartSImulationStage = createStageOne();
        SimStage = createStageTwo();
        StartSImulationStage.show();
    }
    private Stage createStageOne() {
        StartSImulationStage = new Stage(StageStyle.DECORATED);
        StartSImulationStage.setTitle("Stage 1");
        StartSImButton = new Button("Click to start simulation");
        StartSImButton.setOnAction(e -> SimStage.show());
        vbox = new VBox(StartSImButton);
        StartSimScene = new Scene(vbox, 200, 50);
        StartSImulationStage.setScene(StartSimScene);
        return StartSImulationStage;
    }

    private Stage createStageTwo() throws FileNotFoundException {
        restartMap();
        SimStage = new Stage(StageStyle.DECORATED);
        SimStage.setTitle("Stage 2");
        SimStage.initOwner(StartSImulationStage);
        SimStage.initModality(Modality.APPLICATION_MODAL);
        SimScene = new Scene(userArgs, 700, 700);
        SimStage.setScene(SimScene);
        return SimStage;
    }

    private void restartMap() throws FileNotFoundException {
        clearTheMap();
        makeTheMap();
        makeLabelXY();
        makeColumns();
        makeRows();
        placeObjects();
    }

    private void makeTheMap() {
        grid.setGridLinesVisible(true);
        grid.getColumnConstraints().add(new ColumnConstraints(40));
        grid.getRowConstraints().add(new RowConstraints(40));
    }

    private void clearTheMap() {
        grid.setGridLinesVisible(false);
        grid.getChildren().clear();
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();
    }

    private void makeLabelXY() {
        Label xy = new Label("y/x");
        GridPane.setHalignment(xy, HPos.CENTER);
        grid.add(xy, 0, 0);
    }

    private void makeRows() {
        for (int i = 1; i <= upperRight.getY() + 1; i++){
            Label label = new Label("" + (upperRight.getY() - i + 1));
            System.out.println(label);
            grid.getRowConstraints().add(new RowConstraints(40));
            GridPane.setHalignment(label, HPos.CENTER);
            grid.add(label, 0, i);
        }
        System.out.println(grid);
    }

    private void makeColumns() {
        for (int i = 1; i <= upperRight.getX() + 1; i++){
            Label label = new Label("" + (i - 1));
            grid.getColumnConstraints().add(new ColumnConstraints(40));
            GridPane.setHalignment(label, HPos.CENTER);
            grid.add(label, i, 0);
        }
    }

    private void placeObjects() throws FileNotFoundException {
        for (int x = 0; x <= upperRight.getX(); x++){
            for (int y = 0; y <= upperRight.getY(); y++){
                Vector position = new Vector(x, y);
                if (map.objectAt(position) != null) {
                    IMapElement object = map.objectAt(position);
                    VBox vbox = new GuiElementBox(object).getVerticalBox();
                    grid.add(vbox, position.getX() + 1, upperRight.getY() - position.getY() + 1);
                    GridPane.setHalignment(vbox, HPos.CENTER);
                }
            }
        }
    }
    public void positionChanged(Vector oldPosition, Vector newPosition) {
        Platform.runLater(() -> {
            try {
                restartMap();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
