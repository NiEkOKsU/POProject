package project.gui;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
    private final VBox simStats = new VBox();
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
        SimScene = new Scene(userArgs, 700, 800);
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
        placeStats();
    }

    private void placeStats() {
        Stats simulationStats = new Stats(map);
        Text text1 = new Text("Ilosc zwierzakow: " + simulationStats.getNumOfAnimals());
        VBox animalsNum = new VBox(text1);
        animalsNum.setAlignment(Pos.CENTER);
        grid.add(animalsNum, 0, upperRight.getX() + 2);
        Text text2 = new Text("Ilosc kempek trawy: " + simulationStats.getAmmOfGras());
        VBox grassAmm = new VBox(text2);
        grassAmm.setAlignment(Pos.CENTER);
        grid.add(grassAmm, 0, upperRight.getX() + 3);
        Text text3 = new Text("Ilosc pustych pol: " + simulationStats.getNumOfFreeScetors());
        VBox freeScetors = new VBox(text3);
        freeScetors.setAlignment(Pos.CENTER);
        grid.add(freeScetors, 0, upperRight.getX() + 4);
        Text text4 = new Text("Najpopularniejszy genotyp: " + simulationStats.mostPopularGenotype());
        VBox mostPopGenotype = new VBox(text4);
        mostPopGenotype.setAlignment(Pos.CENTER);
        grid.add(mostPopGenotype, 0, upperRight.getX() + 5);
        Text text5 = new Text("Srednia energia zyjacych zwierzakow: " + simulationStats.eneregyMean());
        VBox energyMean = new VBox(text5);
        energyMean.setAlignment(Pos.CENTER);
        grid.add(energyMean, 0, upperRight.getX() + 6);
        Text text6 = new Text("Srednia dlugosc zycia zwierzaka: " + simulationStats.meanOfAgeDeathAnimals());
        VBox meanAgeOfLife = new VBox(text6);
        meanAgeOfLife.setAlignment(Pos.CENTER);
        grid.add(meanAgeOfLife, 0, upperRight.getX() + 7);

    }

    private void makeTheMap() {
        grid.getColumnConstraints().add(new ColumnConstraints(40));
        grid.getRowConstraints().add(new RowConstraints(40));
    }

    private void clearTheMap() {
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
            grid.getRowConstraints().add(new RowConstraints(40));
            GridPane.setHalignment(label, HPos.CENTER);
            grid.add(label, 0, i);
        }
        for (int i = upperRight.getY() + 2; i <= upperRight.getY() + 7; i++){
            grid.getRowConstraints().add(new RowConstraints(40));
        }
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
