package project;

public interface IMap {

    boolean canMoveTo(Vector position);

    boolean place(Animal animal);

    boolean isOccupied(Vector position);

    Object objectAt(Vector position);

    void reachedBoundary(Animal animal);

}
