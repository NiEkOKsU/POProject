package project;

import java.util.HashMap;

public class Portal extends AbstractWorldMap{
    private final int energyToTeleport;
    private final int energyToReproduce = 15;

    public Portal(int width, int height, int numOfGrass, int energyToTeleport) {
        super(width, height, numOfGrass);
        this.energyToTeleport = energyToTeleport;
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

    @Override
    public Vector getPosition() {
        return null;
    }
}
