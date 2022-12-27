package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Animal {
    private MapDirections direction;
    private Vector position;
    private int energy;
    private IMap map;

    private List<IPositionChangeObserver> observers = new ArrayList<>();

    public Animal(IMap map, Vector position) {
        this.direction = MapDirections.NORTH;
        this.position = position;
        this.energy= energy;
        this.map = map;
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

    public int getEnergy(){
        return energy;
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