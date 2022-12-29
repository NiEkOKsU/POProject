package project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Reproduction {
    protected ArrayList<Animal> animals;
    private final Vector position;
    private final int energyFromParent = 8;
    private final int energyToReproduce = 15;
    public Reproduction(ArrayList<Animal> animals, Vector key) {
        this.animals = animals;
        this.position = key;
    }


    public ArrayList<Animal> makingChildrens(){
        for(int i = 0; i < animals.size(); i += 2){
            Animal animal1 = animals.get(i);
            Animal animal2 = animals.get(i + 1);
            if (animal1.getEnergy() >= energyToReproduce && animal2.getEnergy() >= energyToReproduce){
                Animal children = new Animal(animals.get(i).getMap(), animals.get(i).getPosition(), animal1, animal2);
                animal1.setEnergy(animal1.getEnergy() - energyFromParent);
                animal2.setEnergy(animal2.getEnergy() - energyFromParent);
                children.setEnergy(energyFromParent*2);
                System.out.println(children);
                animals.add(1, children);
                System.out.println(animals);
            }
        }
        return animals;
    }
}
