package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Animal implements IMapElement {
    private MapDirections direction;
    private Vector position;
    private int energy;
    private int energyByEat;
    private int energyToMove = 5;
    private IMap map;
    private Genotype genes;
    private List<IPositionChangeObserver> observers = new ArrayList<>();
    private int age;
    private int childrens;
    private int currentGen;
    public Animal(IMap map, Vector position, int energy, int energyByEat) {
        this.direction = MapDirections.NORTH;
        this.position = position;
        this.energy = energy;
        this.map = map;
        this.genes = new Genotype();
        this.energyByEat = energyByEat;
        currentGen = 0;
    }

    public Animal(IMap map, Vector position, Animal animal1, Animal animal2) {
        this.direction = MapDirections.NORTH;
        this.position = position;
        this.map = map;
        this.genes = new Genotype(animal1, animal2);
        currentGen = 0;
    }

    @Override
    public String getImage()  {
        return "animal.png";
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

    public void move(){
        int lenghtOfDNA = genes.lenghtOfGenes();
        MapDirections newDirection = getGenOnIdx(currentGen);
        Vector newPosition = position;
        if (map.eatGrass(newPosition)){
            setEnergy(getEnergy() + energyByEat);
        }
        if (getEnergy() < 0 ){
            map.animalsIsDead(this);
            System.out.println(this);
        }
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
        positionChanged(position, newPosition);
        position = newPosition;

        setEnergy(getEnergy() - energyToMove);
        direction = newDirection;
        age += 1;
        currentGen += 1;
        currentGen %= lenghtOfDNA;

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