package project;

public class RunProject {

    public static void main(String[] args) {

        IMap map = new Portal(7, 5);
        Animal eagle = new Animal(map, new Vector(2,2));

        map.place(eagle);

        System.out.println(map);
        eagle.move(MapDirections.EAST);
        map.reachedBoundary(eagle);
        System.out.println(map);
        eagle.move(MapDirections.EAST);
        map.reachedBoundary(eagle);
        System.out.println(map);
        eagle.move(MapDirections.EAST);
        map.reachedBoundary(eagle);
        System.out.println(map);
        eagle.move(MapDirections.EAST);
        map.reachedBoundary(eagle);
        System.out.println(map);
        eagle.move(MapDirections.EAST);
        map.reachedBoundary(eagle);
        System.out.println(map);
        eagle.move(MapDirections.EAST);
        map.reachedBoundary(eagle);
        System.out.println(map);
        eagle.move(MapDirections.EAST);
        map.reachedBoundary(eagle);
        System.out.println(map);

    }
}
