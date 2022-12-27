package project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractWorldMap implements IMap, IPositionChangeObserver{
    protected final int mapWidth;
    protected final int mapHeight;
    protected HashMap<Vector, Animal> animals;
    private List<IPositionChangeObserver> observers = new ArrayList<>();

    protected AbstractWorldMap(int width, int height) {
        animals = new HashMap<>();
        mapWidth = width;
        mapHeight = height;
    }

    @Override
    public void positionChanged(Vector oldPosition, Vector newPosition){
        Animal animal = animals.get(oldPosition);
        animals.remove(oldPosition);
        animals.put(newPosition, animal);
    }

    @Override
    public boolean place(Animal animal){
        if (this.canMoveTo(animal.getPosition())){
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

    public Vector findLeftBottomCorner() {
        return new Vector(0,0);
    }

    public Vector findRightTopCorner() {
        return new Vector(mapWidth, mapHeight);
    }

    public void reproduction(){
        HashMap<Vector, Integer> OccupiedSectors = new HashMap<>();
        animals.forEach((key, value)->{
            System.out.println(value);
            if(OccupiedSectors.containsKey(key)){
                OccupiedSectors.replace(key, OccupiedSectors.get(key) + 1);
            }
            else{
                OccupiedSectors.put(key, 1);
            }
        });
        OccupiedSectors.forEach((key, value)->{
            System.out.println(value);
            if (value >= 2){
                Animal[] animalsOnSector = new Animal[value];
                final int[] idx = {0};
                animals.forEach((key2, value2) -> {
                    if(key == key2){
                        animalsOnSector[idx[0]] = (value2);
                        idx[0] += 1;
                    }
                });
                Arrays.sort(animalsOnSector, new SortByEnergy());
                //animals.put(animalsOnSector[0].getPosition(), new Animal(animalsOnSector[0].getMap(), key, animalsOnSector[0], animalsOnSector[1]));
            }
        });
    }

    public String toString() {
        MapVisualizer mapVisualizer=new MapVisualizer(this);
        return mapVisualizer.draw(findLeftBottomCorner(), findRightTopCorner());
    }

}
