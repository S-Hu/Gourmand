/**
 * Created by HuShunxin on 16/3/15.
 */
interface Eatable {
    void beEaten(double eatenPart);
}

public abstract class Fruit {
    protected double remaining;

    public double getRemaining() {
        return remaining;
    }
}

final class Watermelon extends Fruit implements Eatable {
    public Watermelon() {
        remaining = 100.0;
    }

    @Override
    public void beEaten(double eatenPart) {
        if (eatenPart > 0) {
            if (remaining > eatenPart) {
                remaining -= eatenPart;
            } else remaining = 0;
            //TODO:make noise
        }
    }
}