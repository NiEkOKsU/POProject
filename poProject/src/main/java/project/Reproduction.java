package project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Reproduction {
    protected ArrayList<Animal> animals;
    private final Vector position;
    public Reproduction(ArrayList<Animal> animals, Vector key) {
        this.animals = animals;
        this.position = key;
    }


    public ArrayList<Animal> makingChildrens(){
        for(int i = 0; i < animals.size(); i += 2){
            Animal children = new Animal(animals.get(i).getMap(), animals.get(i).getPosition(), animals.get(i), animals.get(i + 1));
            System.out.println(children);
            animals.add(1, children);
            System.out.println(animals);
        }
        return animals;
    }
}
