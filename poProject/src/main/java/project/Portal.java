package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Portal extends AbstractWorldMap{
    private final int energyToTeleport;
    private int energyToReproduce = 15;

    public Portal(int width, int height, int numOfGrass, int energyToTeleport) throws FileNotFoundException {
        super(width, height, numOfGrass);
        this.energyToTeleport = energyToTeleport;
        String strCutter = ": ";
        File plik = new File("src/main/java/project/gui/DaneSymulacji.txt");
        Scanner odczyt = new Scanner(plik);
        for(int i = 0; i < 14; i++){
            odczyt.nextLine();
        }
        energyToReproduce = Integer.parseInt(odczyt.nextLine().split(strCutter)[1]);
    }

    @Override
    public boolean canMoveTo(Animal animal) {
        if (isOccupied(animal.getPosition()) && animal.getEnergy() >= energyToReproduce) {
            return true;
        } else if (!isOccupied(animal.getPosition())){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void reachedBoundary(Animal animal) {
        Vector animalPos = animal.getPosition();
        if (animalPos.x < 0 || animalPos.x > mapWidth || animalPos.y < 0 || animalPos.y > mapHeight){
            Vector potentialPosition = new Vector(getRandomNumber(0, mapWidth), getRandomNumber(0,mapHeight));
            while (isOccupied(potentialPosition)){
                potentialPosition = new Vector(getRandomNumber(0, mapWidth), getRandomNumber(0, mapHeight));
            }
            animal.setEnergy(animal.getEnergy()-energyToTeleport);
            animal.positionChanged(animal.getPosition(), potentialPosition);
            animal.setPosition(potentialPosition);
        }

    }


}
