package project;

import java.util.Comparator;

public class SortByAge implements Comparator<Animal> {

    public int compare(Animal a, Animal b)
    {
        return a.getAge() - b.getAge() ;
    }
}
