package project;

import java.util.Comparator;

public class SortByChildrens implements Comparator<Animal> {
    public int compare(Animal a, Animal b)
    {
        return a.getChildrens() - b.getChildrens() ;
    }
}