
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
     * @param toEat 被吃的对象
     * @return 实际吃了多少量
     */
    int takeABite(Eatable toEat);

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
public class Animal implements Eater, Runnable {
    /**
     * 胃类
     *
     * @author HuShunxin
     */
    public class Stomach {
        /**
         * 肚量
         */
        private int capacity;
        /**
         * 已吃
         */
        private int nowEaten;

        public Stomach(int capacity) {
            this.capacity = capacity;
            this.nowEaten = 0;
        }

        /**
         * @return 剩余可吃空间
         */
        public int getEmpty() {
            return capacity - nowEaten;
        }

        /**
         * 填肚子方法
         *
         * @param amount 要填进去的量
         * @return 确实填进去的量
         */
        public int fill(int amount) {
            if (amount > 0) {
                if (amount >= getEmpty()) {
                    amount = getEmpty();
                }
                nowEaten += amount;
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
    protected int amountPerBite;

    /**
     * 嚎叫方法(吃饱/吃完时嚎叫)
     */
    public void cry() {
        System.out.println("bark");
    }

    //吃货任务序列
    private Eatable[] toEats;
    public Eatable[] getToEats() {
        return toEats;
    }
    public void setToEats(Eatable[] toEats) {
        this.toEats = toEats;
    }

    public int tag;
    public AnimalBehaviourDelegate delegate;

    public Animal(Stomach stomach, int eatingSpeed, int amountPerBite, Eatable[] toEats, int tag, AnimalBehaviourDelegate delegate) {
        this.stomach = stomach;
        this.eatingSpeed = eatingSpeed;
        this.amountPerBite = amountPerBite;
        this.toEats = toEats;
        this.tag = tag;
        this.delegate = delegate;
    }

    public Animal(Eatable[] toEats, int tag, AnimalBehaviourDelegate delegate) {
        amountPerBite = 1 + (int) (Math.random() * 2);
        eatingSpeed = 1000 + (int) (Math.random() - 0.5) * 500;
        stomach = new Stomach(50);
        this.toEats = toEats;
        this.tag = tag;
        this.delegate = delegate;
    }

    //线程相关
    @Override
    public void run() {
        for (Eatable toEat : toEats) {
            while (!this.isFull() && toEat.getRemainingAmount() != 0) {
                try {
                    Thread.currentThread().sleep(eatingSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int amount = this.takeABite(toEat);
                if (toEat.isClear())
                    delegate.didEatAFruit(this,(Fruit) toEat);
                else
                    delegate.didEatABite(this,(Fruit) toEat, toEat.getRemainingAmount());
            }
        }
        //叫一声
        this.cry();
        this.delegate.didEndCompetition(this);
        Thread.currentThread().interrupt();
    }

    @Override
    public int takeABite(Eatable toEat) {
        //要吃多少
        int toEatAmount = amountPerBite + (int) Math.floor(Math.random() * 2);
        if (toEatAmount > this.stomach.getEmpty()) toEatAmount = this.stomach.getEmpty();
        if (toEatAmount > toEat.getRemainingAmount()) toEatAmount = toEat.getRemainingAmount();

        //吃了多少
        int realEatAmount = toEat.beEaten(toEatAmount);

        System.out.println(toEat.getRemainingAmount());
        return this.stomach.fill(realEatAmount);
    }

    @Override
    public boolean isFull() {
        return this.stomach.getEmpty() == 0;
    }
}