package project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractWorldMap implements IMap, IPositionChangeObserver{
    protected final int mapWidth;
    protected final int mapHeight;
    protected HashMap<Vector, ArrayList<Animal>> animals;
    private List<IPositionChangeObserver> observers = new ArrayList<>();

    protected AbstractWorldMap(int width, int height) {
        animals = new HashMap<>();
        mapWidth = width;
        mapHeight = height;
    }

    @Override
    public void positionChanged(Vector oldPosition, Vector newPosition){
        Animal animal = animals.get(oldPosition).get(0);
        animals.get(oldPosition).remove(0);
        if(!isOccupied(newPosition)){
            ArrayList<Animal> newList = new ArrayList<>();
            newList.add(animal);
            animals.put(newPosition, newList);
        }
        else{
            ArrayList<Animal> listt = animals.get(newPosition);
            listt.add(animal);
            animals.replace(newPosition, listt);
        }
    }

    @Override
    public boolean place(Animal animal){
        if (!this.isOccupied(animal.getPosition()) && this.canMoveTo(animal.getPosition())){
            ArrayList<Animal> newList = new ArrayList<>();
            newList.add(animal);
            animals.put(animal.getPosition(), newList);
            animal.addObserver(this);
            return true;
        }
        else if(this.canMoveTo(animal.getPosition())){
            ArrayList<Animal> listt = animals.get(animal.getPosition());
            listt.add(animal);
            animals.replace(animal.getPosition(), listt);
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

    public Vector findLeftBottomCorner() {
        return new Vector(0,0);
    }

    public Vector findRightTopCorner() {
        return new Vector(mapWidth, mapHeight);
    }

    public void reproduction(){
        animals.forEach((key, value)->{
            if (value.size() > 1){
                value = new Reproduction(value, key).makingChildrens();
                animals.replace(key, value);
            }
        });
    }

    public String toString() {
        MapVisualizer mapVisualizer=new MapVisualizer(this);
        return mapVisualizer.draw(findLeftBottomCorner(), findRightTopCorner());
    }

}
