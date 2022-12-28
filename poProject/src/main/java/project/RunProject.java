package project;

import javafx.application.Application;
import project.gui.App;

public class RunProject {

    public static void main(String[] args) {

            IMap map = new Earth(10, 10, 40);
            Animal eagle = new Animal(map, new Vector(2,2));
            System.out.println(eagle.getGenes());
            map.place(eagle);
            System.out.println(map);
            eagle.move();
            map.reachedBoundary(eagle);
            System.out.println(map);
            eagle.move();
            map.reachedBoundary(eagle);
            System.out.println(map);
            eagle.move();
            map.reachedBoundary(eagle);
            System.out.println(map);
            eagle.move();
            map.reachedBoundary(eagle);
            System.out.println(map);
            eagle.move();
            map.reachedBoundary(eagle);
            System.out.println(map);
            eagle.move();
            map.reachedBoundary(eagle);
            System.out.println(map);
            eagle.move();
            map.reachedBoundary(eagle);
            System.out.println(map);
            eagle.move();
            map.reachedBoundary(eagle);
            System.out.println(map);
            eagle.move();
            map.reachedBoundary(eagle);
            System.out.println(map);
            eagle.move();
            map.reachedBoundary(eagle);
            System.out.println(map);
            eagle.move();
            map.reachedBoundary(eagle);
            System.out.println(map);
            eagle.move();
            map.reachedBoundary(eagle);
            System.out.println(map);

    }
}
