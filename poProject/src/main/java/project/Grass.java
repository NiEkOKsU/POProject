package project;

public class Grass implements IMapElement{
    private final Vector position;
    public Grass(Vector position) {
        this.position = position;
    }

    @Override
    public String getImage() {
        return "grass.jpg";
    }

    public Vector getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "*";
    }
}
