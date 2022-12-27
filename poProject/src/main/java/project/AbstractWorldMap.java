package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractWorldMap implements IMap, IPositionChangeObserver{

    protected HashMap<Vector, Animal> animals = new HashMap<Vector, Animal>();
    private List<IPositionChangeObserver> observers = new ArrayList<>();

    protected AbstractWorldMap() {
        animals = new HashMap<>();
    }

    @Override
    public void positionChanged(Vector oldPosition, Vector newPosition){
        Animal animal = animals.get(oldPosition);
        animals.remove(oldPosition);
        animals.put(newPosition, animal);
    }

    @Override
    public boolean place(Animal animal){
        if (!this.isOccupied(animal.getPosition()) && this.canMoveTo(animal.getPosition())){
            animals.put(animal.getPosition(), animal);
            animal.addObserver(this);
            return true;
        }
        throw new IllegalArgumentException(animal.getPosition() + " avaliable for this animal");
    }

    @Override
    public boolean isOccupied(Vector position){
        return (animals.containsKey(position));
    }

    @Override
    public Object objectAt(Vector position){
        return animals.get(position);
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public abstract Vector findLeftBottomCorner();
    public abstract Vector findRightTopCorner();

    public String toString() {
        MapVisualizer mapVisualizer=new MapVisualizer(this);
        return mapVisualizer.draw(findLeftBottomCorner(), findRightTopCorner());
    }



}
