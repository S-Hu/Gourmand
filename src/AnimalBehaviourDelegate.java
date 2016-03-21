/**
 * Created by ChenLetian on 3/20/16.
 */

/**
 * 动物行为事件代理类
 */
public interface AnimalBehaviourDelegate {

    /**
     * 动物吃了一口某水果
     * @param animal 动物
     * @param eaten 吃的水果
     * @param remainingAmount 那个水果剩下的量
     */
    public void didEatABite(Animal animal, Fruit eaten, int remainingAmount);

    /**
     * 动物吃完了一个水果
     * @param animal 动物
     * @param eaten 吃完的水果
     */
    public void didEatAFruit(Animal animal, Fruit eaten);

    /**
     * 动物完成了比赛
     * @param animal 动物
     */
    public void didEndCompetition(Animal animal);

}
