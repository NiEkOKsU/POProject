package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationEngine implements IEngine, Runnable{

    private int moveDelay;
    private final IMap map;

    private int numOfAnimals;
    private int energyByEat;
    private int numOfGrass;
    private int startingEnergy;
    //private int animalIsFed;

    //private int lenghtOfGenes;
    //private int maxMitations;
    //private int minMutations;


    private final List<Animal> animals = new ArrayList<Animal>();


    public SimulationEngine(int moveDelay, IMap map, int numOfAnimals, int startingEnergy, int energyByEat, int numOfGrass) {
        this.moveDelay = moveDelay;
        this.map = map;
        this.numOfAnimals = numOfAnimals;
        this.startingEnergy = startingEnergy;
        this.energyByEat = energyByEat;
        this.numOfGrass = numOfGrass;
        placeAnimals(this.numOfAnimals);
    }

    public void placeAnimals(int amountOfAnimals) {
        int itr = 0;
        while (itr!=amountOfAnimals){
            Vector potentialPosition = new Vector(getRandomNumber(0,map.getMapWidth()), getRandomNumber(0,map.getMapHeight()));
            if (!map.isOccupiedByAnimal(potentialPosition)){
                Animal cuteAnimal = new Animal(map, potentialPosition, startingEnergy, energyByEat);
                map.place(cuteAnimal);
                animals.add(cuteAnimal);
                itr += 1;
            }
        }
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


    @Override
    public void run() {
        while (true){
            System.out.println(map);
            for (int i = 0; i < animals.size(); i++){
                Animal animal = animals.get(i);
                animal.move();
                map.reachedBoundary(animal);
                if (animal.getEnergy() < 0){
                    map.animalsIsDead(animal);
                    animals.remove(animals.get(i));
                }
            }
            map.placeInitGrass(numOfGrass);
            try{
                Thread.sleep(moveDelay);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
