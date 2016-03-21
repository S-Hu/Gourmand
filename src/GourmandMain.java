import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by ChenLetian on 3/20/16.
 */
public class GourmandMain implements AnimalBehaviourDelegate {

    private JPanel mainPanel;
    private JButton startButton;
    private JButton resetButton;
    private BackgroundPanel competitionPanel;

    // 西瓜的JLabel
    private JLabel[][] watermelonLabels = new JLabel[8][6];
    // 动物的JLabel
    private JLabel[] animalLabels = new JLabel[8];
    // 奖杯的JLabel
    private JLabel trophyLabel;
    // 动物的名字
    private String[] animalNames = {"cow", "donkey", "elephant", "owl", "pig", "rooster", "sheep", "tiger"};

    // 西瓜的图片
    private ImageIcon[] watermelonPics = new ImageIcon[8];
    // 动物的图片
    private ImageIcon[] animalPics = new ImageIcon[8];
    // 当前比赛是否已经完成
    private boolean hasCompleteCompetition;
    // 当前已经完成比赛的动物数
    private int hasCompleteCount;
    // 获得胜利的动物
    private JLabel winnedAnimalLabel;
    private int winnedAnimalTag;

    // 坐标帮助类
    private CoordinateHelper coorHelper = new CoordinateHelper(new Point(54, 16), new Size(420, 380));

    // 背景音乐(吃西瓜)线程
    private Thread bgmThread;
    // 背景音乐播放器
    private Player bgmPlayer;

    /**
     * 初始化
     */
    public GourmandMain() {

        // 初始化西瓜和动物的图片
        Rectangle frame = coorHelper.getCoordinateOfGrid(1, 1);
        for (int i = 1; i <= 8; i++) {
            String imgPath = "resource/picture/watermelon/watermelon" + String.valueOf(i) + ".png";
            ImageIcon img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(imgPath).getScaledInstance(frame.size.width, frame.size.height, Image.SCALE_SMOOTH));
            watermelonPics[i-1] = img;
            imgPath = "resource/picture/animal/" + animalNames[i-1] + ".png";
            img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(imgPath).getScaledInstance(frame.size.width, frame.size.height, Image.SCALE_SMOOTH));
            animalPics[i-1] = img;
        }

        // 重置比赛
        resetGame();

        // 为按钮增加监听器
        resetButton.addActionListener(e -> resetGame());
        startButton.addActionListener(e -> startGame());
    }

    /**
     * 重置游戏
     */
    private void resetGame() {
        // 重置游戏
        hasCompleteCompetition = false;
        startButton.setEnabled(true);
        hasCompleteCount = 0;
        // 重置界面
        SwingUtilities.invokeLater(() -> {
            // 移除原有JLabel
            competitionPanel.removeAll();
            // 添加备用的奖状图
            ImageIcon img = new ImageIcon("resource/picture/trophy.png");
            trophyLabel = new JLabel(img);
            trophyLabel.setBounds(150, 100, 200, 200);
            trophyLabel.setVisible(false);
            competitionPanel.add(trophyLabel);
            // 添加watermelon的JLabel
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
            // 添加动物的JLable
            for (int animal = 1; animal <= 8; animal++) {
                Rectangle frame = coorHelper.getCoordinateOfGrid(animal, 0);
                JLabel label = new JLabel(animalPics[animal - 1]);
                label.setLocation(frame.origin.x, frame.origin.y);
                label.setSize(frame.size.width, frame.size.height);
                animalLabels[animal - 1] = label;
                competitionPanel.add(label);
            }
            // 添加背景图
            ImageIcon backgroundImage = new ImageIcon("resource/picture/ground.jpg");
            JLabel background = new JLabel(backgroundImage);
            background.setLocation(0, 0);
            background.setSize(500, 400);
            competitionPanel.add(background);

            competitionPanel.repaint();
        });
    }

    /**
     * 开始游戏
     */
    public void startGame() {
        // 开启8只动物吃西瓜的进程
        for (int i = 0; i < 8; i++) {
            Watermelon[] toEats = {new Watermelon(0), new Watermelon(1), new Watermelon(2), new Watermelon(3), new Watermelon(4), new Watermelon(5)};
            Animal animal = new Animal(toEats, i, this);
            new Thread(() -> {
                animal.run();
            }).start();
        }
        // 开启背景音乐(吃西瓜的声音)的线程,并重复播放,直到hasCompleteCompetition为true
        bgmThread = new Thread(() -> {
            while (!hasCompleteCompetition) {
                try {
                    BufferedInputStream buffer = new BufferedInputStream(new FileInputStream("resource/sound/eating.mp3"));
                    Player player = new Player(buffer);
                    player.play();
                    bgmPlayer = player;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }
        });
        bgmThread.start();
        resetButton.setEnabled(false);
    }

    // 吃了一口代理方法
    @Override
    public void didEatABite(Animal animal, Fruit eaten, int remainingAmount) {
        SwingUtilities.invokeLater(() -> {
            watermelonLabels[animal.tag][eaten.tag].setIcon(watermelonPics[8 - remainingAmount]);
        });
    }

    // 吃完了一个水果代理方法
    @Override
    public void didEatAFruit(Animal animal, Fruit eaten) {
        SwingUtilities.invokeLater(() -> {
            watermelonLabels[animal.tag][eaten.tag].setVisible(false);
            Rectangle frame = coorHelper.getCoordinateOfGrid(animal.tag + 1, eaten.tag + 1);
            animalLabels[animal.tag].setLocation(frame.origin.x, frame.origin.y);
        });
    }

    // 某个动物完成了比赛的代理方法
    @Override
    public void didEndCompetition(Animal animal) {
        hasCompleteCompetition = true;
        setupMP3WithFileURL("resource/sound/" + animalNames[animal.tag] + ".mp3");
        hasCompleteCount++;
        if (hasCompleteCount == 8) {
            competitionAllEnd();
        }
        if (hasCompleteCount == 1) {
            winnedAnimalLabel = animalLabels[animal.tag];
            winnedAnimalTag = animal.tag;
        }
    }

    // 所有动物都完成了比赛
    public void competitionAllEnd() {
        resetButton.setEnabled(true);
        startButton.setEnabled(false);
        trophyLabel.setVisible(true);
        winnedAnimalLabel.setSize(70, 70);
        winnedAnimalLabel.setIcon(new ImageIcon(animalPics[winnedAnimalTag].getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH)));
        winnedAnimalLabel.setLocation(250 - 35, 170 - 35);
        setupMP3WithFileURL("resource/sound/" + animalNames[winnedAnimalTag] + ".mp3");
    }

    // 启动音频
    private void setupMP3WithFileURL(String url) {
        new Thread(() -> {
            try {
                BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(url));
                Player player = new Player(buffer);
                player.play();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("吃西瓜大赛");
        frame.setContentPane(new GourmandMain().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void createUIComponents() {
        competitionPanel = new BackgroundPanel();
    }
}
