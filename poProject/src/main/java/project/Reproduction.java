package project;

import project.gui.App;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Reproduction {
    protected ArrayList<Animal> animals;
    private ArrayList<Animal> childrens = new ArrayList<>();
    private App app;
    private IMap map;
    public Reproduction(ArrayList<Animal> animals, App app, IMap map) {
        this.animals = animals;
        this.app = app;
        this.map = map;
    }


    public ArrayList<Animal> makingChildrens() throws FileNotFoundException {
        for(int i = 0; i < animals.size() - 1; i += 2){
            Animal animal1 = animals.get(i);
            Animal animal2 = animals.get(i + 1);
            int energyToReproduce = 15;
            if (animal1.getEnergy() >= energyToReproduce && animal2.getEnergy() >= energyToReproduce){
                Animal children = new Animal(animals.get(i).getMap(), animals.get(i).getPosition(), animal1, animal2);
                String strCutter = ": ";
                File plik = new File("src/main/java/project/gui/DaneSymulacji.txt");
                Scanner odczyt = new Scanner(plik);
                for(int j = 0; j < 15; j++){
                    odczyt.nextLine();
                }
                int energyToRemove = Integer.parseInt(odczyt.nextLine().split(strCutter)[1]);
                int energyFromParent = energyToRemove / 2;
                if(energyToRemove % 2 == 0){
                    animal1.setEnergy(animal1.getEnergy() - energyFromParent);
                    animal2.setEnergy(animal2.getEnergy() - energyFromParent);
                }
                else{
                    if(animal1.getEnergy() > animal2.getEnergy()){
                        animal1.setEnergy(animal1.getEnergy() - energyFromParent - 1);
                        animal2.setEnergy(animal2.getEnergy() - energyFromParent);
                    }
                    else{
                        animal1.setEnergy(animal1.getEnergy() - energyFromParent);
                        animal2.setEnergy(animal2.getEnergy() - energyFromParent - 1);
                    }
                }
                children.setEnergy(energyToRemove);
                children.addObserver(app);
                children.addObserver((IPositionChangeObserver) map);
                animals.add(childrens.size(), children);
            }
        }
        return animals;
    }
}
