package project;

import java.util.HashMap;

public class Portal extends AbstractWorldMap{

    private final int mapWidth;
    private final int mapHeight;

    public Portal(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    @Override
    public boolean canMoveTo(Vector position) {
        //tu trzeba uwzględnić energię
        return true;
    }

    @Override
    public void reachedBoundary(Animal animal) {
        Vector animalPos = animal.getPosition();
        if (animalPos.x < 0 || animalPos.x > mapWidth || animalPos.y < 0 || animalPos.y > mapHeight){
            System.out.println("out");
            Vector potentialPosition = new Vector(getRandomNumber(0, mapWidth), getRandomNumber(0,mapHeight));
            while (isOccupied(potentialPosition)){
                potentialPosition = new Vector(getRandomNumber(0, mapWidth), getRandomNumber(0, mapHeight));
            }
            animal.positionChanged(animal.getPosition(), potentialPosition);
            animal.setPosition(potentialPosition);
        }

    }
    @Override
    public Vector findLeftBottomCorner() {
        return new Vector(0,0);
    }

    @Override
    public Vector findRightTopCorner() {
        return new Vector(mapWidth, mapHeight);
    }
}
