
package main;

import character.Player;

import javax.swing.*;
import java.awt.*;

public class ScoreManager {

    public static void showFinalScore(Player player, int stagesCleared, int turnsUsed) {
        // Save score
        LeaderboardManager.addScore(player.getName(), stagesCleared, turnsUsed);

        // Format text with proper font to avoid rectangle issue
        String message = String.format(
            "<html><b>Player Name:</b> %s<br><b>Stages Cleared:</b> %d<br><b>Total Turns:</b> %d</html>",
            player.getName(), stagesCleared, turnsUsed
        );

        JLabel label = new JLabel(message);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16)); 
        JOptionPane.showMessageDialog(null, label, "Final Score", JOptionPane.INFORMATION_MESSAGE);

        // Show leaderboard
        LeaderboardManager.showLeaderboard();
    }
}
