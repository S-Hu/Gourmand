/**
 * Created by ChenLetian on 3/20/16.
 */
public interface AnimalBehaviourDelegate {

    public void didEatABite(Animal animal, Fruit eaten, int remainingAmount);
    public void didEatAFruit(Animal animal, Fruit eaten);
    public void didEndCompetition(Animal animal);

}
