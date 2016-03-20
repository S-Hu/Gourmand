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

    private JLabel[][] watermelonLabels = new JLabel[8][6];
    private JLabel[] animalLabels = new JLabel[8];
    private String[] animalNames = {"cow", "donkey", "elephant", "owl", "pig", "rooster", "sheep", "tiger"};

    private CoordinateHelper coorHelper = new CoordinateHelper(new Point(54, 16), new Size(420, 380));

    public GourmandMain() {

        resetGame();
        resetButton.addActionListener(e -> resetGame());
        startButton.addActionListener(e -> startGame());
    }

    public void resetGame() {

        competitionPanel.removeAll();

        for (int row = 1; row <= 8; row++) {
            for (int column = 1; column <= 6; column++) {
                Rectangle frame = coorHelper.getCoordinateOfGrid(row, column);
                String imgPath = "resource/picture/watermelon/WTMelon" + String.valueOf(column) + ".jpg";
                ImageIcon image = new ImageIcon(new ImageIcon(imgPath).getImage().getScaledInstance(frame.size.width, frame.size.height, Image.SCALE_DEFAULT));
                JLabel label = new JLabel(image);
                label.setLocation(frame.origin.x, frame.origin.y);
                label.setSize(frame.size.width, frame.size.height);
                competitionPanel.add(label);
                watermelonLabels[row - 1][column - 1] = label;
            }
        }
        for (int animal = 1; animal <= 8; animal++) {
            Rectangle frame = coorHelper.getCoordinateOfGrid(animal, 0);
            String imgPath = "resource/picture/animal/" + animalNames[animal - 1] + ".jpg";
            ImageIcon image = new ImageIcon(new ImageIcon(imgPath).getImage().getScaledInstance(frame.size.width, frame.size.height, Image.SCALE_DEFAULT));
            JLabel label = new JLabel(image);
            label.setLocation(frame.origin.x, frame.origin.y);
            label.setSize(frame.size.width, frame.size.height);
            competitionPanel.add(label);
        }

        ImageIcon backgroundImage = new ImageIcon("resource/picture/ground.jpg");
        JLabel background = new JLabel(backgroundImage);
        background.setLocation(0, 0);
        background.setSize(500, 400);
        competitionPanel.add(background);

    }

    public void startGame() {
        
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GourmandMain");
        frame.setContentPane(new GourmandMain().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        competitionPanel = new BackgroundPanel();
        Image img = new ImageIcon("resource/picture/ground.jpg").getImage();
        competitionPanel.setImage(img);

    }
}
