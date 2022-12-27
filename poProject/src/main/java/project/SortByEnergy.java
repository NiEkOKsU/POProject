package project;

import java.util.Comparator;

public class SortByEnergy implements Comparator<Animal> {
    public int compare(Animal a, Animal b)
    {
        return a.getEnergy() - b.getEnergy() ;
    }
}
