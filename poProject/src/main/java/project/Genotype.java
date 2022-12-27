package project;

public class Genotype {
    private final int numOfGenes = 6;
    public final int bornAnimalEnergy = 10;
    private final int minimalMutations = 0;
    private final int maximalMutations = 20;
    public MapDirections[] genes = new MapDirections[numOfGenes];
    public Genotype(Animal animal1, Animal animal2){
        float energySum = animal1.energy + animal2.energy;
        int numOfGenesFromParent1 = (int)(numOfGenes * (animal1.energy/energySum));
        int side = (int)(Math.random()*2);
        switch (side){
            case 0 -> {
                int i;
                for(i = 0; i < numOfGenesFromParent1; i++){
                    genes[i] = animal1.genes[i];
                }
                for(i = numOfGenesFromParent1; i < numOfGenes; i++){
                    genes[i] = animal2.genes[i];
                }
            }
            case 1 ->{
                int i;
                for(i = 0; i < numOfGenes - numOfGenesFromParent1; i++){
                    genes[i] = animal2.genes[i];
                }
                for(i = numOfGenes - numOfGenesFromParent1; i < numOfGenes; i++){
                    genes[i] = animal1.genes[i];
                }
            }
        }
        int toRem = (int)(bornAnimalEnergy * (animal1.energy/energySum));
        animal1.energy -= toRem;
        animal2.energy -= bornAnimalEnergy - toRem;
        System.out.println(animal1.energy);
        System.out.println(animal2.energy);
        mutations();
    }

    public Genotype() {
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

    private void mutations(){
        int mutation;
        for (int i = 0; i < numOfGenes; i++) {
            mutation = (int)(Math.random() * 3) - 1;
            if(mutation == 1){
                genes[i] = genes[i].next();
            }
            else if(mutation == -1){
                genes[i] = genes[i].previous();
            }
        }
    }

}
