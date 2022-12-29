package project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Stats {
    private final AbstractWorldMap map;

    private final List<Animal> animals = new ArrayList<>();
    private int ammOfgrass = 0;
    public Stats(AbstractWorldMap map) {
        this.map = map;
        findElemsOnMap();
    }

    private void findElemsOnMap(){
        for (int x = 0; x <= map.mapWidth; x++){
            for (int y = 0; y <= map.mapHeight; y++){
                Vector position = new Vector(x, y);
                if (map.isOccupiedByAnimal(position)) {
                    List<Animal> animalsOnPosition = map.getAnimalsAtPosition(position);
                    animals.addAll(animalsOnPosition);
                }
                if (map.isOccupiedByGrass(position)) {
                    ammOfgrass += 1;
                }
            }
        }
    }

    public String getNumOfAnimals(){
        return "" + animals.size();
    }

    public String getAmmOfGras(){
        return "" + ammOfgrass;
    }

    public String getNumOfFreeScetors(){
        int freeScetors = 0;
        for (int x = 0; x <= map.mapWidth; x++){
            for (int y = 0; y <= map.mapHeight; y++){
                Vector position = new Vector(x, y);
                if (!map.isOccupied(position)) {
                    freeScetors += 1;
                }
            }
        }
        return "" + freeScetors;
    }

    public String mostPopularGenotype(){
        int[] numOfGenes = new int[8];
        Arrays.fill(numOfGenes, 0);
        animals.forEach(animal ->{
            MapDirections[] genes = animal.getGenes().genes;
            for(MapDirections gene: genes){
                switch(gene){
                    case NORTH -> numOfGenes[0] += 1;
                    case NORTH_EAST -> numOfGenes[1] += 1;
                    case EAST -> numOfGenes[2] += 1;
                    case SOUTH_EAST -> numOfGenes[3] += 1;
                    case SOUTH -> numOfGenes[4] += 1;
                    case SOUTH_WEST -> numOfGenes[5] += 1;
                    case WEST -> numOfGenes[6] += 1;
                    case NORTH_WEST -> numOfGenes[7] += 1;
                }
            }
        });
        int maxID = 0;
        for(int i = 1; i < numOfGenes.length; i ++){
            if(numOfGenes[i] > numOfGenes[maxID]){
                maxID = i;
            }
        }
        return switch (maxID){
            case 0 -> MapDirections.NORTH.toString();
            case 1 -> MapDirections.NORTH_EAST.toString();
            case 2 -> MapDirections.EAST.toString();
            case 3 -> MapDirections.SOUTH_EAST.toString();
            case 4 -> MapDirections.SOUTH.toString();
            case 5 -> MapDirections.SOUTH_WEST.toString();
            case 6 -> MapDirections.WEST.toString();
            case 7 -> MapDirections.NORTH_WEST.toString();
            default -> throw new IllegalStateException("Unexpected value: " + maxID);
        };
    }

    public String eneregyMean(){
        double energySum = 0;
        double sizeOfAnimalsPopulation = 0;
        for(Animal animal : animals){
            if(animal.getEnergy() > -1){
                energySum += animal.getEnergy();
                sizeOfAnimalsPopulation += 1;
            }
        }
        double toRet = energySum / sizeOfAnimalsPopulation;
        return "" + toRet ;
    }

    public String meanOfAgeDeathAnimals(){
        double ageSum = 0;
        double numOfDeathAnimals = 0;
        for(Animal animal : animals){
            if(animal.getEnergy() == -1){
                ageSum += animal.getAge();
                numOfDeathAnimals += 1;
            }
        }
        double toRet =ageSum / numOfDeathAnimals;
        return "" + toRet;
    }
}
