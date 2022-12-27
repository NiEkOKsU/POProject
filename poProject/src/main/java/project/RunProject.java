package project;

public class RunProject {

    public static void main(String[] args) {

        IMap map = new Portal(5, 5);
        Animal eagle = new Animal(map, new Vector(2,2));
        Animal doggo = new Animal(map, new Vector(2,2));

        map.place(eagle);
        System.out.println(map);
        map.place(doggo);
        System.out.println(map);
        //eagle.move(MapDirections.NORTH);
        //map.reachedBoundary(eagle);
        //System.out.println(map);
        //doggo.move(MapDirections.WEST);
        //map.reachedBoundary(doggo);
        //System.out.println(map);
        //map.reproduction();
        //System.out.println(map);
        //eagle.move(MapDirections.SOUTH);
        //map.reachedBoundary(eagle);
        //System.out.println(map);
        //doggo.move(MapDirections.EAST);
        //map.reachedBoundary(doggo);
        //System.out.println(map);
        //eagle.move(MapDirections.EAST);
        //map.reachedBoundary(eagle);
        //System.out.println(map);
        //eagle.move(MapDirections.EAST);
        //map.reachedBoundary(eagle);
        //System.out.println(map);
        //eagle.move(MapDirections.EAST);
        //map.reachedBoundary(eagle);
        //System.out.println(map);

    }
}
