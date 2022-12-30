package project;

import project.gui.App;

import java.util.ArrayList;

public class Reproduction {
    protected ArrayList<Animal> animals;
    private App app;
    public Reproduction(ArrayList<Animal> animals, App app) {
        this.animals = animals;
        this.app = app;
    }


    public ArrayList<Animal> makingChildrens(){
        for(int i = 0; i < animals.size() - 1; i += 2){
            Animal animal1 = animals.get(i);
            Animal animal2 = animals.get(i + 1);
            int energyToReproduce = 15;
            if (animal1.getEnergy() >= energyToReproduce && animal2.getEnergy() >= energyToReproduce){
                Animal children = new Animal(animals.get(i).getMap(), animals.get(i).getPosition(), animal1, animal2);
                int energyFromParent = 8;
                animal1.setEnergy(animal1.getEnergy() - energyFromParent);
                animal2.setEnergy(animal2.getEnergy() - energyFromParent);
                children.setEnergy(energyFromParent *2);
                children.addObserver(app);
                animals.add(animals.size(), children);
            }
        }
        return animals;
    }
}
