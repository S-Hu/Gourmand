import javax.swing.*;
import java.awt.*;

/**
 * Created by ChenLetian on 3/20/16.
 */
public class BackgroundPanel extends JPanel {

    private Image img;

    public void setImage(Image image) {
        this.img = image;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(img, 0, 0, 500, 400, this);
        System.out.print(this.getWidth());
        System.out.print(this.getHeight());
    }
}
