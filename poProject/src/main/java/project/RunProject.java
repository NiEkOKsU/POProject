package project;

import javafx.application.Application;
import project.gui.App;

public class RunProject {

    public static void main(String[] args) {

            IMap map = new Earth(20, 20, 40);
            Animal eagle = new Animal(map, new Vector(2,2));
            Animal doggo = new Animal(map, new Vector(3,3));
            Application.launch(App.class, args);
            map.place(eagle);
            map.place(doggo);
            map.placeInitGrass(100);
            System.out.println(map);
            eagle.move(MapDirections.NORTH);
            map.reachedBoundary(eagle);
            System.out.println(map);
            eagle.move(MapDirections.NORTH);
            map.reachedBoundary(eagle);
            System.out.println(map);
            eagle.move(MapDirections.NORTH);
            map.reachedBoundary(eagle);
            System.out.println(map);

            doggo.move(MapDirections.WEST);
            map.reachedBoundary(doggo);
            System.out.println(map);
            map.reproduction();

            System.out.println(map);
            eagle.move(MapDirections.SOUTH);
            map.reachedBoundary(eagle);
            System.out.println(map);
            doggo.move(MapDirections.EAST);
            map.reachedBoundary(doggo);
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
