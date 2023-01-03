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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App extends Application implements IPositionChangeObserver {
    private AbstractWorldMap map;
    private Vector lowerLeft;
    private Vector upperRight;
    private int mapWidth;
    private int mapHeight;
    private int startingNumOfGrass;
    private int numOfAnimlas;
    private int startingEnergy;
    private int energyByEat;
    private int numOfGrass;
    private int portalEnergy;
    private final GridPane grid = new GridPane();
    private final VBox userArgs = new VBox(grid);
    private final VBox simStats = new VBox();
    private Stage StartSImulationStage;
    private Scene StartSimScene;
    private VBox vbox;
    private Button StartSImButton;

    private Stage SimStage;
    private Scene SimScene;
    private boolean start = true;
    private Thread engineThread;
    private SimulationEngine engine;
    public void init() {
        try {
            String strCutter = ": ";
            File plik = new File("src/main/java/project/gui/DaneSymulacji.txt");
            Scanner odczyt = new Scanner(plik);
            mapWidth = Integer.parseInt(odczyt.nextLine().split(strCutter)[1]);
            mapHeight = Integer.parseInt(odczyt.nextLine().split(strCutter)[1]);
            startingNumOfGrass = Integer.parseInt(odczyt.nextLine().split(strCutter)[1]);
            numOfAnimlas = Integer.parseInt(odczyt.nextLine().split(strCutter)[1]);
            startingEnergy = Integer.parseInt(odczyt.nextLine().split(strCutter)[1]);
            energyByEat = Integer.parseInt(odczyt.nextLine().split(strCutter)[1]);
            numOfGrass = Integer.parseInt(odczyt.nextLine().split(strCutter)[1]);
            portalEnergy = Integer.parseInt(odczyt.nextLine().split(strCutter)[1]);
            String mapType = odczyt.nextLine();
            if(mapType.equals("Portal")){
                map = new Portal(mapWidth, mapHeight, startingNumOfGrass, portalEnergy);
            }
            else{
                map = new Earth(mapWidth, mapHeight, startingNumOfGrass);
            }
            engine = new SimulationEngine(500, map, numOfAnimlas, startingEnergy, energyByEat, numOfGrass, this);
            lowerLeft = new Vector(0,0);
            upperRight = new Vector(mapWidth,mapHeight);
        }
        catch (IllegalArgumentException | FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StartSImulationStage = createStageOne();
        SimStage = createStageTwo();
        StartSImulationStage.show();
    }

    private void ButtonEvent(){
        SimStage.show();
        engineThread = new Thread(engine);
        engineThread.start();
    }

    private Stage createStageOne() {
        StartSImulationStage = new Stage(StageStyle.DECORATED);
        StartSImulationStage.setTitle("Stage 1");
        StartSImButton = new Button("Click to start simulation");
        StartSImButton.setOnAction(e -> ButtonEvent());
        vbox = new VBox(StartSImButton);
        StartSimScene = new Scene(vbox, 200, 50);
        StartSImulationStage.setScene(StartSimScene);
        return StartSImulationStage;
    }

    private Stage createStageTwo() throws FileNotFoundException {
        restartMap();
        placeButton();
        SimStage = new Stage(StageStyle.DECORATED);
        SimStage.setTitle("Stage 2");
        SimStage.initOwner(StartSImulationStage);
        SimStage.initModality(Modality.APPLICATION_MODAL);
        SimScene = new Scene(userArgs, 700, 800);
        SimStage.setScene(SimScene);
        return SimStage;
    }

    public void restartMap() throws FileNotFoundException {
        clearTheMap();
        makeTheMap();
        makeLabelXY();
        makeColumns();
        makeRows();
        placeObjects();
        placeStats();
    }

    private void placeButton() {
        Button button = new Button("Stop");
        button.setOnAction(e -> {
            this.start = !this.start;
            if(this.start){
                button.setText("Stop");
                engineThread.resume();
            }
            else{
                button.setText("Resume");
                engineThread.suspend();
            }
        });
        VBox vbox = new VBox(button);
        vbox.setAlignment(Pos.CENTER);
        userArgs.getChildren().add(button);
    }

    private void placeStats() {
        Stats simulationStats = new Stats(map, engine.getAnimals());
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
        for (int i = upperRight.getY() + 2; i <= upperRight.getY() + 7; i++){
            grid.getColumnConstraints().add(new ColumnConstraints(80));
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
    @Override
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
