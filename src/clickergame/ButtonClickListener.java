package clickergame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonClickListener implements ActionListener {

    private Game instance;

    public ButtonClickListener(Game instance) {
        this.instance = instance;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

    /*
        Handles the back Button
     */

        if (command.equals("back")) {
            instance.displayGame();
        }

    /*
       Handles Purchasing Upgrades
    */

        if (command.startsWith("buy")){
            String[] input = command.split("-");
            for (Upgrade u : instance.getUpgrades()) {
                if (input[1].equals(u.getRawName())) {
                    if (instance.adjustScore(-u.getCost())){
                        u.purchase();
                        u.updateLabels();
                    } else {
                        Toolkit.getDefaultToolkit().beep();
                    }
                }
            }
        }

    /*
        Handles the clicker button
     */

        else if (command.equals("clickerButtonClicked")) {
            instance.adjustScore(1);
        }

    /*
        Handles loading the upgrade screen
     */

        else if (command.equals("upgrades")) {
            instance.displayUpgrades();

        }

        instance.redrawFrame();
    }
}




