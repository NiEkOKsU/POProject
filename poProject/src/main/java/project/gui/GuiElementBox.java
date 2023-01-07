package project.gui;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import project.IMapElement;

import java.io.FileNotFoundException;

public class GuiElementBox {

    final static int SIZE = 20;
    private final VBox verticalBox;

    public GuiElementBox (IMapElement element) throws FileNotFoundException {
        Image image = new Image(element.getImage());
        ImageView image1 = new ImageView();
        image1.setImage(image);
        image1.setFitHeight(SIZE);
        image1.setFitWidth(SIZE);
        verticalBox = new VBox(image1);
        verticalBox.setAlignment(Pos.CENTER);
    }

    public VBox getVerticalBox () {
        return verticalBox;
    }
}