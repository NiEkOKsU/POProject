package project;

import java.util.*;

public abstract class AbstractWorldMap implements IMap, IPositionChangeObserver{
    protected final int mapWidth;
    protected final int mapHeight;
    protected final int numOfGrass;
    protected final int equatorStart;
    protected final int equatorEnd;
    protected HashMap<Vector, Animal> animals;
    private List<IPositionChangeObserver> observers = new ArrayList<>();
    private final Map<Vector, Grass> grassMap = new HashMap<>();

    protected AbstractWorldMap(int width, int height, int grasses) {
        animals = new HashMap<>();
        mapWidth = width;
        mapHeight = height;
        equatorStart = mapHeight*4/10;
        equatorEnd = mapHeight*6/10;
        numOfGrass = grasses;
        placeInitGrass(numOfGrass);
    }

    @Override
    public void placeInitGrass(int amountOfGrass) {
        int itr = 0;
        while (itr != amountOfGrass){
            int y = 0;
            int randomNumber = getRandomNumber(0,10);
            if (randomNumber > 1){
                y = getRandomNumber(equatorStart, equatorEnd + 1);
            } else {
                int randomNumber2 = getRandomNumber(0, 2);
                if (randomNumber2==0){
                    y = getRandomNumber(0, equatorStart);
                } else {
                    y = getRandomNumber(equatorEnd + 1, mapHeight + 1);
                }
            }

            int x = getRandomNumber(0, mapWidth + 1);
            Vector potentialPosition = new Vector(x, y);
            if (!isOccupiedByGrass(potentialPosition)){
                grassMap.put(potentialPosition, new Grass(potentialPosition));
                itr += 1;
            }

        }

    }

    public boolean isOccupiedByGrass(Vector position) {
        return (grassMap.containsKey(position));
    }

    public boolean isOccupiedByAnimal(Vector position) {
        return (animals.containsKey(position));
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
            System.out.println(mapHeight);
            animals.put(animal.getPosition(), animal);
            animal.addObserver(this);
            return true;
        }
        throw new IllegalArgumentException(animal.getPosition() + " avaliable for this animal");
    }

    @Override
    public boolean isOccupied(Vector position){
        return (isOccupiedByAnimal(position) || isOccupiedByGrass(position));
    }

    @Override
    public Object objectAt(Vector position){
        if (animals.get(position) != null){
            return animals.get(position);
        } else {
            return grassMap.get(position);
        }
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

    @Override
    public boolean eatGrass(Vector position) {
        if (isOccupiedByGrass(position) && isOccupiedByAnimal(position)){
            grassMap.remove(position);
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        MapVisualizer mapVisualizer=new MapVisualizer(this);
        return mapVisualizer.draw(findLeftBottomCorner(), findRightTopCorner());
    }

}
