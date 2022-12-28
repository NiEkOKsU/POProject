package project.gui;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.*;

import java.io.FileNotFoundException;
import java.util.AbstractMap;
public class App extends Application {
    private AbstractWorldMap map;
    private Vector lowerLeft = new Vector(0,0);
    private Vector upperRight = new Vector(20,20);
    private final GridPane grid = new GridPane();

    public void init() {
        try {
            map = new Earth(20, 20, 40);
            map.place(new Animal(map, new Vector(2,2)));
        }
        catch (IllegalArgumentException exception) {
            exception.printStackTrace();
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        restartMap();
        Label label = new Label("Zwierzak");
        Scene scene = new Scene(label, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void restartMap() throws FileNotFoundException {

        checkTheBoundaries();
        clearTheMap();
        makeTheMap();

        makeLabelXY();

        makeColumns(upperRight);

        makeRows(upperRight);
        System.out.println('b');
    }

    private void checkTheBoundaries() {
        lowerLeft = map.findLeftBottomCorner();
        upperRight = map.findRightTopCorner();
    }

    private void makeTheMap() {
        grid.setGridLinesVisible(true);
        grid.getColumnConstraints().add(new ColumnConstraints(upperRight.getX()));
        grid.getRowConstraints().add(new RowConstraints(upperRight.getY()));
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

    private void makeRows(Vector upperRight) {
        for (int i = 1; i <= upperRight.getY() + 1; i++){
            Label label = new Label("" + (upperRight.getY() - i + 1));
            grid.getRowConstraints().add(new RowConstraints(upperRight.getY()));
            GridPane.setHalignment(label, HPos.CENTER);
            grid.add(label, 0, i);
        }
    }

    private void makeColumns(Vector upperRight) {
        for (int i = 1; i <= upperRight.getX() + 1; i++){
            Label label = new Label("" + (i - 1));
            grid.getColumnConstraints().add(new ColumnConstraints(upperRight.getX()));
            GridPane.setHalignment(label, HPos.CENTER);
            grid.add(label, i, 0);
        }
    }


    private VBox addStartButtonAndTextField() {
        TextField textField = new TextField();
        Button button = new Button("Let's get this party started!");
        return new VBox(textField, button);
    }

}
