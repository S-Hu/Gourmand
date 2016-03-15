/**
 * Created by HuShunxin on 16/3/15.
 */

interface Eater {
    void takeABite();
}

public abstract class Animal implements Eater{
    protected double eatingSpeed;
    protected double stomachCapacity;

    public abstract void cry();

    @Override
    public void takeABite() {
        //TODO
    }
}

class Dog extends Animal{
    public Dog() {
        eatingSpeed=1;
        stomachCapacity=1;
    }

    @Override
    public void cry() {
        //TODO:bark
    }
}