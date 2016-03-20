import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ChenLetian on 3/20/16.
 */
public class GourmandMain {
    private JPanel mainPanel;
    private JButton startButton;
    private JButton resetButton;
    private JButton button3;
    private BackgroundPanel competitionPanel;

    public GourmandMain() {

        Image img = new ImageIcon("resource/picture/ground.jpg").getImage();
        competitionPanel.setImage(img);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GourmandMain");
        frame.setContentPane(new GourmandMain().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
