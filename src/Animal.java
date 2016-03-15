
/**
 * Created by HuShunxin on 16/3/15.
 */

/**
 * Something can eat.
 *
 * @author HuShunxin
 */
interface Eater {
    /**
     * 吃一口方法
     *
     * @param toEat 被吃的对象
     */
    void takeABite(Eatable toEat);

    /**
     * @return 是否吃饱了
     */
    boolean isFull();
}

/**
 * 动物类
 *
 * @author HuShunxin
 */
public abstract class Animal implements Eater {
    /**
     * 胃类
     *
     * @author HuShunxin
     */
    public class Stomach {
        /**
         * 肚量
         */
        private double capacity;
        /**
         * 已吃
         */
        private double size;

        public Stomach(double capacity) {
            this.capacity = capacity;
            this.size = 0;
        }

        /**
         * @return 剩余可吃空间
         */
        public double getEmpty() {
            return capacity - size;
        }

        /**
         * 填肚子方法
         *
         * @param amount 要填进去的量
         * @return 确实填进去的量
         */
        public double fill(double amount) {
            if (amount > 0) {
                if (amount >= getEmpty()) {
                    amount = getEmpty();
                }
                size += amount;
            } else amount = 0;
            return amount;
        }
    }

    /**
     * 该动物的胃
     */
    protected Stomach stomach;
    /**
     * 吃的速度(两次吃的时间间隔)
     */
    protected int eatingSpeed;
    /**
     * 每次吃的量
     */
    protected double amountPerBite;

    /**
     * 嚎叫方法(吃饱/吃完时嚎叫)
     */
    public abstract void cry();

    @Override
    public void takeABite(Eatable toEat) {
        //要吃多少
        double toEatAmount = amountPerBite * (1 + 0.5 * Math.random());
        if (toEatAmount > this.stomach.getEmpty()) toEatAmount = this.stomach.getEmpty();
        if (toEatAmount > toEat.getRemainingAmount()) toEatAmount = toEat.getRemainingAmount();

        //吃了多少
        this.stomach.fill(toEat.beEaten(toEatAmount));
    }

    @Override
    public boolean isFull() {
        return this.stomach.getEmpty() == 0;
    }
}

/**
 * Dog: an example
 */
class Dog extends Animal implements Runnable {
    //吃货任务序列
    private Eatable[] toEats;

    public Eatable[] getToEats() {
        return toEats;
    }

    public void setToEats(Eatable[] toEats) {
        this.toEats = toEats;
    }

    public Dog() {
        amountPerBite = 10 + (Math.random() - 0.5) * 5;
        eatingSpeed = 1000 + (int) (Math.random() - 0.5) * 200;
        stomach = new Stomach(200 + (Math.random() - 0.5) * 100);
    }

    @Override
    public void cry() {
        //TODO:bark
        System.out.println("dog bark");
    }

    //线程相关
    @Override
    public void run() {
        for (Eatable toEat : toEats) {
            while (!this.isFull() && toEat.getRemainingAmount() != 0) {
                this.takeABite(toEat);
            }
        }
        //叫一声
        this.cry();
    }
}