/**
 * Created by HuShunxin on 16/3/15.
 */

/**
 * Something can be eaten.
 *
 * @author HuShunxin
 */
interface Eatable {
    /**
     * @return remaining amount of this eatable.
     */
    double getRemainingAmount();

    /**
     * @return if this eatable is clear.
     */
    boolean isClear();

    /**
     * 被吃方法
     *
     * @param eatenAmount 将要被吃掉的量
     * @return 确实被吃掉的量
     */
    double beEaten(double eatenAmount);
}

/**
 * 水果类
 *
 * @author HuShunxin
 */
public abstract class Fruit implements Eatable {
    protected double remaining;

    @Override
    public double getRemainingAmount() {
        return remaining;
    }

    @Override
    public boolean isClear() {
        return this.getRemainingAmount() == 0;
    }

    @Override
    public double beEaten(double eatenAmount) {
        if (eatenAmount > 0) {
            if (remaining < eatenAmount) {
                eatenAmount = remaining;
            }
            remaining -= eatenAmount;
            //TODO:make some noise
            return eatenAmount;
        } else return 0;
    }
}

/**
 * 西瓜类
 *
 * @author HuShunxin
 */
final class Watermelon extends Fruit {
    public Watermelon() {
        remaining = 100.0;// + Math.random() * 20;
    }
}
