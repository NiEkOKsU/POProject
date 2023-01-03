package project;

import project.gui.App;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimulationEngine implements IEngine, Runnable{
    private App app;

    private int moveDelay;
    private final IMap map;
    private int numOfAnimals;
    private int energyByEat;
    private int numOfGrass;
    private int startingEnergy;


    private final List<Animal> animals = new ArrayList<Animal>();

    public List<Animal> getAnimals() {
        return animals;
    }

    public SimulationEngine(int moveDelay, IMap map, int numOfAnimals, int startingEnergy, int energyByEat, int numOfGrass, App app) throws FileNotFoundException {
        this.moveDelay = moveDelay;
        this.map = map;
        this.numOfAnimals = numOfAnimals;
        this.startingEnergy = startingEnergy;
        this.energyByEat = energyByEat;
        this.numOfGrass = numOfGrass;
        this.app = app;
        placeAnimals(this.numOfAnimals);
    }

    public void placeAnimals(int amountOfAnimals) throws FileNotFoundException {
        int itr = 0;
        while (itr!=amountOfAnimals){
            Vector potentialPosition = new Vector(getRandomNumber(0, map.getMapWidth()), getRandomNumber(0,map.getMapHeight()));
            if (!map.isOccupiedByAnimal(potentialPosition)){
                Animal cuteAnimal = new Animal(map, potentialPosition, startingEnergy, energyByEat);
                map.place(cuteAnimal);
                animals.add(cuteAnimal);
                cuteAnimal.addObserver(app);
                itr += 1;
            }
        }
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private void sleepingMachine(){
        try{
            Thread.sleep(moveDelay);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        int day = 0;
        while (true){
            for (int i = 0; i < animals.size(); i++){
                Animal animal = animals.get(i);
                if(animal.getEnergy() > 0){
                    animal.move();
                    animal.addObserver(app);
                    map.reachedBoundary(animal);
                    if (animal.getEnergy() <= 0){
                        map.animalsIsDead(animal);
                        animal.setDiedAt(day);
                        animal.setEnergy(-1);
                        animal.removeObserver(app);
                    }
                }
                sleepingMachine();
            }

            map.placeInitGrass(numOfGrass);
            sleepingMachine();
            animals.sort(new SortByChildrens());
            animals.sort(new SortByAge());
            animals.sort(new SortByEnergy());
            HashMap<Vector, ArrayList<Animal>> animalsMap = map.getAnimalMap();
            animalsMap.forEach((key, value) -> {
                if(value.size() > 1){
                    value.sort(new SortByChildrens());
                    value.sort(new SortByAge());
                    value.sort(new SortByEnergy());
                    int sizeBeforeRep = value.size();
                    try {
                        new Reproduction(value, app, map).makingChildrens();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    if(sizeBeforeRep < value.size()){
                        int numOfNewAnimals = value.size() - sizeBeforeRep;
                        for(int i = value.size() - 1; i > value.size() - 1 - numOfNewAnimals; i--){
                            animals.add(value.get(i));
                        }
                    }
                    sleepingMachine();
                }
            });
            sleepingMachine();
            day+=1;
        }
    }
}
