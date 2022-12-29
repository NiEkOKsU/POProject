package project;

public class Earth extends AbstractWorldMap{

    private final int energyToReproduce = 15;
    public Earth(int width, int height, int numOfGrass){
        super(width, height, numOfGrass);
    }

    public void reachedPole(Animal animal){
        if (animal.getPosition().y < 0){
            Vector currentPosition = animal.getPosition();
            animal.setDirection(animal.getDirection().reverse());
            animal.getGenes().reverse();
            animal.positionChanged(currentPosition, new Vector(currentPosition.x, 0));
            animal.setPosition(new Vector(currentPosition.x, 0));
        }
        if (animal.getPosition().y > mapHeight){
            Vector currentPosition = animal.getPosition();
            animal.setDirection(animal.getDirection().reverse());
            animal.getGenes().reverse();
            animal.positionChanged(currentPosition, new Vector(currentPosition.x, mapHeight));
            animal.setPosition(new Vector(currentPosition.x, mapHeight));
        }
    }

    public void aroundTheWorld(Animal animal){
        Vector animalPos = animal.getPosition();
        if (animalPos.x < 0 ){
            Vector secVector = new Vector(animalPos.x%(mapWidth+1), animalPos.y);
            positionChanged(animalPos,secVector);
            animal.setPosition(secVector);

        } else if (animalPos.x > mapWidth){
            Vector secVector = new Vector(animalPos.x%(mapWidth+1), animalPos.y);
            positionChanged(animalPos,secVector);
            animal.setPosition(secVector);
        }

    }
    @Override
    public boolean canMoveTo(Animal animal) {
        if (isOccupied(animal.getPosition()) && animal.getEnergy() >= energyToReproduce) {
            return true;
        } else if (!isOccupied(animal.getPosition())){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void reachedBoundary(Animal animal) {
        aroundTheWorld(animal);
        reachedPole(animal);
    }


}
