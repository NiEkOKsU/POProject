package project;

import javafx.application.Application;
import project.gui.App;

public class RunProject {

    public static void main(String[] args) {

        int mapWidth = 10;
        int mapHeight = 10;
        int startingNumOfGrass = 8;
        int numOfAnimlas = 5;
        int startingEnergy = 20;
        int energyByEat = 8;
        int numOfGrass = 5;

        int portalEnergy = 10;

        //Application.launch(App.class, args);
        IEngine engine = new SimulationEngine(500, new Portal(mapWidth, mapHeight, startingNumOfGrass, portalEnergy),
                numOfAnimlas, startingEnergy, energyByEat, numOfGrass);
        engine.run();
    }
}
