package project;

public class Earth extends AbstractWorldMap{

    public Earth(int width, int height){
        super(width, height);
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
    public boolean canMoveTo(Vector position) {
        //tu trzeba uwzględnić energię
        System.out.println("xd");
        return true;
    }

    @Override
    public void reachedBoundary(Animal animal) {
        aroundTheWorld(animal);
        reachedPole(animal);
    }
}
