package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Earth extends AbstractWorldMap{

    private int energyToReproduce;
    public Earth(int width, int height, int numOfGrass) throws FileNotFoundException {
        super(width, height, numOfGrass);
        String strCutter = ": ";
        File plik = new File("src/main/java/project/gui/DaneSymulacji.txt");
        Scanner odczyt = new Scanner(plik);
        for(int i = 0; i < 14; i++){
            odczyt.nextLine();
        }
        energyToReproduce = Integer.parseInt(odczyt.nextLine().split(strCutter)[1]);
    }

    public void reachedPole(Animal animal){
        if (animal.getPosition().y < 0){
            Vector currentPosition = animal.getPosition();
            animal.setDirection(animal.getDirection().reverse());
            animal.getGenes().reverse();
            animal.positionChanged(currentPosition, new Vector(currentPosition.x, 0));
            animal.setPosition(new Vector(currentPosition.x, 0));
        }
        if (animal.getPosition().y > mapHeight){
            Vector currentPosition = animal.getPosition();
            animal.setDirection(animal.getDirection().reverse());
            animal.getGenes().reverse();
            animal.positionChanged(currentPosition, new Vector(currentPosition.x, mapHeight));
            animal.setPosition(new Vector(currentPosition.x, mapHeight));
        }
    }

    public void aroundTheWorld(Animal animal){
        Vector animalPos = animal.getPosition();
        if (animalPos.x < 0 ){
            Vector secVector = new Vector(animalPos.x%(mapWidth+1), animalPos.y);
            positionChanged(animalPos,secVector);
            animal.setPosition(secVector);

        } else if (animalPos.x > mapWidth){
            Vector secVector = new Vector(animalPos.x%(mapWidth+1), animalPos.y);
            positionChanged(animalPos,secVector);
            animal.setPosition(secVector);
        }

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
        aroundTheWorld(animal);
        reachedPole(animal);
    }


}
