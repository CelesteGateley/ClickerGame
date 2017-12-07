package clickergame;

import javax.swing.*;
import java.awt.*;
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

    private double score = 0;

    /*
        Upgrades Setup
     */

    private ArrayList<Upgrade> upgrades = new ArrayList<>();


    private Upgrade cursor = new Upgrade("cursor", 15, 0.1, this);
    private Upgrade granny = new Upgrade("granny", "grannies", 100, 0.4, this);
    private Upgrade farm = new Upgrade("farm", 500, 1, this);

    {
        upgrades.add(cursor);
        upgrades.add(granny);
        upgrades.add(farm);
    }


    private JFrame frame = new JFrame(title);

    private JButton clickerButton = new JButton("Click for Points");

    private JLabel scoreLabel = new JLabel((int) score + " Clicks!", CENTER);
    private JLabel balanceLabel = new JLabel((int) score + " Clicks!", CENTER);

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
        initialise();
    }

    private void initialise() {

        /*
         * Creates the main game frame
         */

        frame.setBounds(100, 100, 450, 300);
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

        upgradeScreen = new JPanel(new GridLayout(3, 1));

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

        JPanel upgradesPanel = new JPanel(new GridLayout(upgrades.size(), 1));

        for (Upgrade u : upgrades) {
            upgradesPanel.add(u.getUpgradeSection());
        }

        JScrollPane upgradesPane = new JScrollPane(upgradesPanel);

        upgradeScreen.add(upgradesPane);
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

    boolean adjustScore(double amount) {

        if (score + amount >= 0) {

            score = Math.round((score + amount) * 10) / 10;

            String scoreString = "";

            if (score == (int) score) {
                scoreString += (int) score;
            } else {
                scoreString += score;
            }

            if (score == 1) {
                scoreString += " Click!";
            } else {
                scoreString += " Clicks!";
            }
            scoreLabel.setText(scoreString);
            balanceLabel.setText(scoreString);
            redrawFrame();
            return true;

        } else {
            return false;
        }
    }

    void redrawFrame() {
        frame.repaint();
        frame.setVisible(true);
    }

    void displayUpgrades() {
        frame.getContentPane().removeAll();
        frame.add(upgradeScreen);
        redrawFrame();
    }

    void displayGame() {
        frame.getContentPane().removeAll();
        frame.add(gameScreen);
        redrawFrame();
    }

    ArrayList<Upgrade> getUpgrades() {
        return upgrades;
    }
}