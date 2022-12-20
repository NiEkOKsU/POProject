package project;

public enum MapDirections {
    NORTH,
    NORTH_WEST,
    WEST,
    SOUTH_WEST,
    SOUTH,
    SOUTH_EAST,
    EAST,
    NORTH_EAST;

    @Override
    public String toString() {
        return switch (this) {
            case NORTH -> "Polnoc";
            case SOUTH -> "Poludnie";
            case WEST -> "Zachod";
            case EAST -> "Wschod";
            case NORTH_WEST -> "Polnocny-Zachod";
            case SOUTH_WEST -> "Poludniowy-Zachod";
            case SOUTH_EAST -> "Poludniowy-Wschod";
            case NORTH_EAST -> "Polnocny-Wschod";
        };
    }

    public Vector toUnitVector(){
        return switch (this) {
            case NORTH -> new Vector(0, 1);
            case SOUTH -> new Vector(0, -1);
            case WEST -> new Vector(-1, 0);
            case EAST -> new Vector(1, 0);
            case NORTH_WEST -> new Vector(1, -1);
            case SOUTH_WEST -> new Vector(-1, -1);
            case SOUTH_EAST -> new Vector(-1, 1);
            case NORTH_EAST -> new Vector(1, 1;
        };
    }

    public static void main(String[] args){
    }
}
