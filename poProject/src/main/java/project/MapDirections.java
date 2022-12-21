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

    public MapDirections next(){
        return switch (this) {
            case NORTH -> MapDirections.NORTH_EAST;
            case SOUTH -> MapDirections.SOUTH_WEST;
            case WEST -> MapDirections.NORTH_WEST;
            case EAST -> MapDirections.SOUTH_EAST;
            case NORTH_EAST -> MapDirections.EAST;
            case SOUTH_WEST -> MapDirections.WEST;
            case NORTH_WEST -> MapDirections.NORTH;
            case SOUTH_EAST -> MapDirections.SOUTH;
        };
    }
    public MapDirections previous(){
        return switch (this) {
            case NORTH -> MapDirections.NORTH_WEST;
            case SOUTH -> MapDirections.SOUTH_EAST;
            case WEST -> MapDirections.SOUTH_WEST;
            case EAST -> MapDirections.NORTH_EAST;
            case NORTH_EAST -> MapDirections.NORTH;
            case SOUTH_WEST -> MapDirections.SOUTH;
            case NORTH_WEST -> MapDirections.WEST;
            case SOUTH_EAST -> MapDirections.EAST;
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
            case NORTH_EAST -> new Vector(1, 1);
        };
    }

    public static void main(String[] args){
    }
}
