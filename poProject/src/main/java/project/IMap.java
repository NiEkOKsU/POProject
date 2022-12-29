package project;

public interface IMap {

    boolean canMoveTo(Animal animal);

    boolean place(Animal animal);

    boolean isOccupied(Vector position);

    Object objectAt(Vector position);

    void reachedBoundary(Animal animal);

    void reproduction();

    void placeInitGrass(int amountOfGrass);
    boolean eatGrass(Vector position);
    int getMapHeight();
    int getMapWidth();
    boolean isOccupiedByAnimal(Vector position);
    void animalsIsDead(Animal animal);
}
