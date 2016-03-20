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
    int getRemainingAmount();

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
    int beEaten(int eatenAmount);
}

/**
 * 水果类
 *
 * @author HuShunxin
 */
public abstract class Fruit implements Eatable {
    protected int remaining;

    @Override
    public int getRemainingAmount() {
        return remaining;
    }

    @Override
    public boolean isClear() {
        return this.getRemainingAmount() == 0;
    }

    @Override
    public int beEaten(int eatenAmount) {
        if (eatenAmount > 0) {
            if (remaining < eatenAmount) {
                eatenAmount = remaining;
            }
            remaining -= eatenAmount;
            //TODO:make some noise
            return eatenAmount;
        }
        else
            return 0;
    }

    public int tag;
}

/**
 * 西瓜类
 *
 * @author HuShunxin
 */
final class Watermelon extends Fruit {
    public Watermelon(int tag) {
        remaining = 8;
        this.tag = tag;
    }
}
