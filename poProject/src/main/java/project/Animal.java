package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Animal {
    private MapDirections direction;
    private Vector position;
    private int energy;
    private IMap map;
    private Genotype genes;
    private List<IPositionChangeObserver> observers = new ArrayList<>();

    private int age;
    private int childrens;
    public Animal(IMap map, Vector position) {
        this.direction = MapDirections.NORTH;
        this.position = position;
        this.energy = 10;
        this.map = map;
        this.genes = new Genotype();
    }

    public Animal(IMap map, Vector position, Animal animal1, Animal animal2) {
        this.direction = MapDirections.NORTH;
        this.position = position;
        this.energy = 10;
        this.map = map;
        this.genes = new Genotype(animal1, animal2);
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position=position;
    }

    public MapDirections getDirection() {
        return direction;
    }

    public void setDirection(MapDirections direction) {
        this.direction = direction;
    }

    public int getEnergy(){
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public MapDirections getGenOnIdx(int i){
        return this.genes.genes[i];
    }

    public Genotype getGenes() {
        return genes;
    }

    public IMap getMap() {
        return map;
    }

    public int getAge() {
        return age;
    }

    public int getChildrens() {
        return childrens;
    }

    public void gettingChild() {
        this.childrens += 1;
    }

    public boolean isAt(Vector checkPosition){
        return Objects.equals(position, checkPosition);
    }

    public void move(MapDirections newDirection){
        Vector newPosition = position;
        switch (direction){
            case NORTH -> newPosition = position.add(newDirection.toUnitVector());
            case SOUTH -> newPosition = position.add(newDirection.toUnitVector());
            case WEST -> newPosition = position.add(newDirection.toUnitVector());
            case EAST -> newPosition = position.add(newDirection.toUnitVector());
            case NORTH_EAST -> newPosition = position.add(newDirection.toUnitVector());
            case NORTH_WEST -> newPosition = position.add(newDirection.toUnitVector());
            case SOUTH_EAST -> newPosition = position.add(newDirection.toUnitVector());
            case SOUTH_WEST -> newPosition = position.add(newDirection.toUnitVector());

        }
        if(map.canMoveTo(newPosition)){
            positionChanged(position, newPosition);
            position = newPosition;
        }
        direction = newDirection;
        age += 1;
    }
    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }
    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public String toString() {
        return "A{" +
                "d = " + direction +
                ", p = " + position +
                '}';
    }

    void positionChanged(Vector oldPosition, Vector newPosition){
        for (IPositionChangeObserver observer : observers){
            observer.positionChanged(oldPosition, newPosition);
        }
    }
}