package project;

public class Grass {
    private final Vector position;
    public Grass(Vector position) {
        this.position = position;
    }

    public Vector getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "*";
    }
}
