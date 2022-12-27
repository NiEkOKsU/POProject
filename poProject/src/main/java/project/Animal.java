package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Animal {
    private MapDirections direction;
    private Vector position;
    public int energy;
    public MapDirections[] genes;
    private List<IPositionChangeObserver> observers = new ArrayList<>();

    public Animal(Vector position) {
        this.direction = MapDirections.NORTH;
        this.position = position;
        this.energy = 20;
        this.genes = new Genotype().genes;
    }

    public Animal(Vector position, int energy) {
        this.direction = MapDirections.NORTH;
        this.position = position;
        this.energy = energy;
        this.genes = new Genotype().genes;
    }

    public Vector getPosition() {
        return position;
    }

    public MapDirections getDirection() {
        return direction;
    }

    public boolean isAt(Vector checkPosition){
        return Objects.equals(position, checkPosition);
    }

    public void move(MapDirections direction){
        Vector newVector = position;
        switch (direction){
            case NORTH, SOUTH, WEST, EAST, NORTH_WEST, SOUTH_WEST, SOUTH_EAST, NORTH_EAST -> {
                newVector = position.add(this.direction.toUnitVector());
                this.direction = direction;
            }
            default -> {}

        }
    }
    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }
    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }
    void positionChanged(Vector oldPosition, Vector newPosition){
        for (IPositionChangeObserver observer : observers){
            observer.positionChanged(oldPosition, newPosition);
        }
    }
}
