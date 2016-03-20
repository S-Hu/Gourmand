import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * Created by ChenLetian on 3/20/16.
 */
public class GourmandMain implements AnimalBehaviourDelegate {
    private JPanel mainPanel;
    private JButton startButton;
    private JButton resetButton;
    private BackgroundPanel competitionPanel;

    private JLabel[][] watermelonLabels = new JLabel[8][6];
    private JLabel[] animalLabels = new JLabel[8];
    private String[] animalNames = {"cow", "donkey", "elephant", "owl", "pig", "rooster", "sheep", "tiger"};

    private ImageIcon[] watermelonPics = new ImageIcon[8];
    private ImageIcon[] animalPics = new ImageIcon[8];

    private CoordinateHelper coorHelper = new CoordinateHelper(new Point(54, 16), new Size(420, 380));

    public GourmandMain() {

        Rectangle frame = coorHelper.getCoordinateOfGrid(1, 1);
        for (int i = 1; i <= 8; i++) {
            String imgPath = "resource/picture/watermelon/watermelon" + String.valueOf(i) + ".png";
            ImageIcon img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(imgPath).getScaledInstance(frame.size.width, frame.size.height, Image.SCALE_SMOOTH));
            watermelonPics[i-1] = img;
            imgPath = "resource/picture/animal/" + animalNames[i-1] + ".png";
            img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(imgPath).getScaledInstance(frame.size.width, frame.size.height, Image.SCALE_SMOOTH));
            animalPics[i-1] = img;
        }

        resetGame();
        resetButton.addActionListener(e -> resetGame());
        startButton.addActionListener(e -> startGame());
    }

    public void resetGame() {
        SwingUtilities.invokeLater(() -> {
            competitionPanel.removeAll();

            for (int row = 1; row <= 8; row++) {
                for (int column = 1; column <= 6; column++) {
                    Rectangle frame = coorHelper.getCoordinateOfGrid(row, column);
                    JLabel label = new JLabel(watermelonPics[0]);
                    label.setLocation(frame.origin.x, frame.origin.y);
                    label.setSize(frame.size.width, frame.size.height);
                    competitionPanel.add(label);
                    watermelonLabels[row - 1][column - 1] = label;
                }
            }
            for (int animal = 1; animal <= 8; animal++) {
                Rectangle frame = coorHelper.getCoordinateOfGrid(animal, 0);
                JLabel label = new JLabel(animalPics[animal - 1]);
                label.setLocation(frame.origin.x, frame.origin.y);
                label.setSize(frame.size.width, frame.size.height);
                animalLabels[animal - 1] = label;
                competitionPanel.add(label);
            }

            ImageIcon backgroundImage = new ImageIcon("resource/picture/ground.jpg");
            JLabel background = new JLabel(backgroundImage);
            background.setLocation(0, 0);
            background.setSize(500, 400);
            competitionPanel.add(background);
            competitionPanel.repaint();
        });
    }

    public void startGame() {
        for (int i = 0; i < 8; i++) {
            Watermelon[] toEats = {new Watermelon(0), new Watermelon(1), new Watermelon(2), new Watermelon(3), new Watermelon(4), new Watermelon(5)};
            Animal animal = new Animal(toEats, i, this);
            new Thread(() -> {
                animal.run();
            }).start();
        }
        new Thread(() -> {
            while (true) {
                try {
                    BufferedInputStream buffer = new BufferedInputStream(new FileInputStream("resource/sound/eating.mp3"));
                    Player player = new Player(buffer);
                    player.play();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void didEatABite(Animal animal, Fruit eaten, int remainingAmount) {
        SwingUtilities.invokeLater(() -> {
            watermelonLabels[animal.tag][eaten.tag].setIcon(watermelonPics[8 - remainingAmount]);
        });
    }

    @Override
    public void didEatAFruit(Animal animal, Fruit eaten) {
        SwingUtilities.invokeLater(() -> {
            watermelonLabels[animal.tag][eaten.tag].setVisible(false);
            Rectangle frame = coorHelper.getCoordinateOfGrid(animal.tag + 1, eaten.tag + 1);
            animalLabels[animal.tag].setLocation(frame.origin.x, frame.origin.y);
        });
    }

    @Override
    public void didEndCompetition(Animal animal) {
        SwingUtilities.invokeLater(() -> {
            try {
                BufferedInputStream buffer = new BufferedInputStream(new FileInputStream("resource/sound/" + animalNames[animal.tag] + ".mp3"));
                Player player = new Player(buffer);
                player.play();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        });
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
        competitionPanel = new BackgroundPanel();
        Image img = new ImageIcon("resource/picture/ground.jpg").getImage();
        competitionPanel.setImage(img);
    }
}
