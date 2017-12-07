package clickergame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TimerTask;

import static javax.swing.SwingConstants.CENTER;

@SuppressWarnings("unused")
public class Game {

    /*
        Constants
     */

    private static final Font LARGE = new Font("default", Font.PLAIN, 30);
    private static final Font MEDIUM = new Font("default", Font.PLAIN, 20);
    private static final Font SMALL = new Font("default", Font.PLAIN, 12);
    private java.util.Timer autoClicker = new java.util.Timer();

    private String title = "Clicker Game";

    public Game instance;

    private long score = 0;

    /*
        Upgrades Setup
     */

    private ArrayList<Upgrade> upgrades = new ArrayList<>();


    private Upgrade cursor = new Upgrade("cursor", 15, 1, this);
    private Upgrade granny = new Upgrade("granny", "grannies", 100, 2, this);
    private Upgrade farm = new Upgrade("farm", 500, 5, this);

    {
        upgrades.add(cursor);
        upgrades.add(granny);
        upgrades.add(farm);
    }


    private JFrame frame = new JFrame(title);

    private JButton clickerButton = new JButton("Click for Points");

    private JLabel scoreLabel = new JLabel(score + " Clicks!", CENTER);
    private JLabel balanceLabel = new JLabel(score + " Clicks!", CENTER);

    private JPanel gameScreen;
    private JPanel upgradeScreen;


    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            try {
                Game window = new Game();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private Game() {
        instance = this;
        initialise();
    }

    private void initialise() {

        /*
         * Creates the main game frame
         */

        frame.setBounds(100, 100, 600, 450);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /*
            Initialises Clicker Button
         */

        clickerButton.setActionCommand("clickerButtonClicked");
        clickerButton.addActionListener(new ButtonClickListener(this));
        clickerButton.setFont(LARGE);

        /*
            Sets the font of the score labels
         */

        scoreLabel.setFont(LARGE);
        balanceLabel.setFont(LARGE);

        /*
            Creates the Switch Screen button
         */

        JButton upgradeScreenSwitch = new JButton("Upgrades");
        upgradeScreenSwitch.setActionCommand("upgrades");
        upgradeScreenSwitch.addActionListener(new ButtonClickListener(this));
        upgradeScreenSwitch.setFont(LARGE);

        /*
           Creates the Game Panel
         */

        gameScreen = new JPanel(new GridLayout(3, 1));
        gameScreen.add(scoreLabel);
        gameScreen.add(clickerButton);
        gameScreen.add(upgradeScreenSwitch);

        /*
           Update Screen Panel
         */

        upgradeScreen = new JPanel(new GridLayout((upgrades.size() + 2), 1));

        /*
            Creates the back button
         */

        JButton backButton = new JButton("Back");
        backButton.setFont(LARGE);
        backButton.setActionCommand("back");
        backButton.addActionListener(new ButtonClickListener(this));

        /*
            Adds the elements to the upgrades screen
         */

        upgradeScreen.add(balanceLabel);

        for (Upgrade u : upgrades) {
            upgradeScreen.add(u.getUpgradeSection());
        }

        upgradeScreen.add(backButton);

        /*
            Sets up the score increment timer
         */

        autoClicker.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (Upgrade u : upgrades) {
                    if (u.getAmount() > 0) {
                        adjustScore(u.getClicks());
                    }
                }

            }
        }, 1000, 1000);

        /*
            Shows the default game screen
         */

        displayGame();

    }

    public boolean adjustScore(double amount) {

        if (score + amount >= 0) {
            score += amount;
            String scoreString;

            if (score == 1) {
                scoreString = (score + " Click!");
            } else {
                scoreString = (score + " Clicks!");
            }
            scoreLabel.setText(scoreString);
            balanceLabel.setText(scoreString);
            redrawFrame();
            return true;

        } else {
            return false;
        }
    }

    public void redrawFrame() {
        frame.repaint();
        frame.setVisible(true);
    }

    public void displayUpgrades() {
        frame.getContentPane().removeAll();
        frame.add(upgradeScreen);
        redrawFrame();
    }

    public void displayGame() {
        frame.getContentPane().removeAll();
        frame.add(gameScreen);
        redrawFrame();
    }

    public ArrayList<Upgrade> getUpgrades() {
        return upgrades;
    }
}