package project;

import java.util.*;

public abstract class AbstractWorldMap implements IMap, IPositionChangeObserver{
    protected final int mapWidth;
    protected final int mapHeight;
    protected final int numOfGrass;
    protected final int equatorStart;
    protected final int equatorEnd;
    protected HashMap<Vector, ArrayList<Animal>> animals;
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
        Animal animal = animals.get(oldPosition).get(0);
        animals.get(oldPosition).remove(0);
        if(animals.get(oldPosition).size() == 0){
            animals.remove(oldPosition);
        }
        if(!isOccupiedByAnimal(newPosition)){
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
        if (!this.isOccupiedByAnimal(animal.getPosition()) && this.canMoveTo(animal)){
            ArrayList<Animal> newList = new ArrayList<>();
            newList.add(animal);
            animals.put(animal.getPosition(), newList);
            animal.addObserver(this);
            return true;
        }
        else if(this.canMoveTo(animal)){
            ArrayList<Animal> newList = animals.get(animal.getPosition());
            System.out.println(newList);
            newList.add(animal);
            animals.replace(animal.getPosition(), newList);
            animal.addObserver(this);
            return true;
        }
        throw new IllegalArgumentException(animal.getPosition() + " avaliable for this animal");
    }

    @Override
    public void animalsIsDead(Animal animal){
        animal.removeObserver(this);
        Vector animalPos = animal.getPosition();
        ArrayList<Animal> animalsAtPos = getAnimalsAtPosition(animalPos);
        if(animalsAtPos != null){
            if(animalsAtPos.size() == 1){
                animals.remove(animalPos);
            }
            else{
                int i = 0;
                if (animalsAtPos.get(i).getEnergy() != animal.getEnergy()){
                    while(animalsAtPos.get(i).getEnergy() != animal.getEnergy()){
                        i++;
                    }
                }
                animalsAtPos.remove(i);
            }
        }
    }

    @Override
    public boolean isOccupied(Vector position){
        return (isOccupiedByAnimal(position) || isOccupiedByGrass(position));
    }

    @Override
    public IMapElement objectAt(Vector position){
        if (animals.get(position) != null){
            return animals.get(position).get(0);
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

    @Override
    public int getMapWidth(){
        return mapWidth;
    }

    @Override
    public int getMapHeight(){
        return mapHeight;
    }

    public HashMap<Vector, ArrayList<Animal>> getAnimalMap(){
        return animals;
    }

    public ArrayList<Animal> getAnimalsAtPosition(Vector position){
        if(isOccupiedByAnimal(position)){
            return animals.get(position);
        }
        return null;
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
