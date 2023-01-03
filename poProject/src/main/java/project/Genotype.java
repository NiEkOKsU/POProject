package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Genotype {
    private int numOfGenes;
    public int bornAnimalEnergy;
    private int minimalMutations;
    private int maximalMutations;
    public MapDirections[] genes;
    public Genotype(Animal animal1, Animal animal2) throws FileNotFoundException {
        simDataSet();
        float energySum = animal1.getEnergy() + animal2.getEnergy();
        int numOfGenesFromParent1 = (int)(numOfGenes * (animal1.getEnergy()/energySum));
        int side = (int)(Math.random()*2);
        switch (side){
            case 0 -> {
                int i;
                for(i = 0; i < numOfGenesFromParent1; i++){
                    genes[i] = animal1.getGenOnIdx(i);
                }
                for(i = numOfGenesFromParent1; i < numOfGenes; i++){
                    genes[i] = animal2.getGenOnIdx(i);
                }
            }
            case 1 ->{
                int i;
                for(i = 0; i < numOfGenes - numOfGenesFromParent1; i++){
                    genes[i] = animal2.getGenOnIdx(i);
                }
                for(i = numOfGenes - numOfGenesFromParent1; i < numOfGenes; i++){
                    genes[i] = animal1.getGenOnIdx(i);
                }
            }
        }
        int toRem = (int)(bornAnimalEnergy * (animal1.getEnergy()/energySum));
        animal1.setEnergy(animal1.getEnergy() - toRem);
        animal2.setEnergy(bornAnimalEnergy - toRem);
        mutations();
    }

    public Genotype() throws FileNotFoundException {
        simDataSet();
        int genome;
        for(int i = 0; i < numOfGenes; i++){
            this.genes[i] = MapDirections.NORTH;
            genome = (int)(Math.random() * 8);
            switch (genome){
                case 0 -> {
                    genes[i] = MapDirections.NORTH;
                }
                case 1 -> {
                    genes[i] = MapDirections.NORTH_WEST;
                }
                case 2 -> {
                    genes[i] = MapDirections.WEST;
                }
                case 3 -> {
                    genes[i] = MapDirections.SOUTH_WEST;
                }
                case 4 -> {
                    genes[i] = MapDirections.SOUTH;
                }
                case 5 -> {
                    genes[i] = MapDirections.SOUTH_EAST;
                }
                case 6 -> {
                    genes[i] = MapDirections.EAST;
                }
                case 7 -> {
                    genes[i] = MapDirections.NORTH_EAST;
                }
            }
        }
    }

    public void simDataSet() throws FileNotFoundException {
        String strCutter = ": ";
        File plik = new File("src/main/java/project/gui/DaneSymulacji.txt");
        Scanner odczyt = new Scanner(plik);
        for(int i = 0; i < 9; i++){
            odczyt.nextLine();
        }
        numOfGenes = Integer.parseInt(odczyt.nextLine().split(strCutter)[1]);
        bornAnimalEnergy = Integer.parseInt(odczyt.nextLine().split(strCutter)[1]);
        minimalMutations = Integer.parseInt(odczyt.nextLine().split(strCutter)[1]);
        maximalMutations = Integer.parseInt(odczyt.nextLine().split(strCutter)[1]);
        genes = new MapDirections[numOfGenes];
    }


    private void mutations(){
        int mutation;
        int numOfMutations = 0;
        int range = 3;
        for (int i = 0; i < numOfGenes; i++) {
            mutation = (int)(Math.random() * range);
            if(mutation == 1){
                genes[i] = genes[i].next();
                numOfMutations += 1;
            }
            else if(mutation == 0){
                genes[i] = genes[i].previous();
                numOfMutations += 1;
            }
            if(numOfMutations == maximalMutations){
                break;
            }
            if(numOfGenes - i - 1 == minimalMutations){
                range = 2;
            }
        }
    }

    public void reverse(){
        for(int i = 0; i < numOfGenes; i++){
            genes[i] = genes[i].reverse();
        }
    }

    public int lenghtOfGenes(){
        return genes.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(genes);
    }
}
