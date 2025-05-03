
package main;

import character.Player;
import javax.swing.*;

public class ScoreManager {

    public static void showFinalScore(Player player, int stagesCleared, int turnsUsed) {
        String message = "Player Name: " + player.getName() + 
                         "\nStages Cleared: " + stagesCleared + 
                         "\nTotal Turns: " + turnsUsed + " เทิร์น";

        JOptionPane.showMessageDialog(null, message, "Final Score", JOptionPane.INFORMATION_MESSAGE);
    }
}
