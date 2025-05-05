
package main;

import character.Player;
import character.Boss;
import character.EnemyStage;
import battle.BlessingEffectManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameFlowManager {
    public static Player player;
    private static List<Boss> enemies;
    private static int stageIndex = 0;
    private static int totalTurns = 0;

    public static void startGame(String playerName) {
        player = new Player(playerName, 100, 30, 10, 15);
        enemies = EnemyStage.getEnemies();
        stageIndex = 0;
        totalTurns = 0;
        nextStage();
    }

    public static void nextStage() {
        int stageNumber = stageIndex + 1;

        if (stageIndex >= enemies.size()) {
            ScoreManager.showFinalScore(player, stageNumber - 1, totalTurns);
            return;
        }

        if (stageNumber % 10 == 5) {
            player.restoreHp();
            JOptionPane.showMessageDialog(null, "You've entered a shop stage! Your HP has been fully restored.");
            new ShopGUI(player, () -> {
                stageIndex++;
                nextStage();
            });
            return;
        }

        Boss currentEnemy = enemies.get(stageIndex);
        new BattleGUI(player, currentEnemy, turnsUsed -> {
            if (!player.isAlive()) {
                JLabel label = new JLabel("Game Over! You lost at stage " + stageNumber);
                label.setFont(new Font("Tahoma", Font.PLAIN, 16));
                JOptionPane.showMessageDialog(null, label, "Game Over", JOptionPane.INFORMATION_MESSAGE);
                ScoreManager.showFinalScore(player, stageNumber - 1, totalTurns);
                return;
            }

            int minGold = 20;
            int maxGold = Math.min(60, (int) (20 + stageIndex * 1.5));
            int goldEarned = ThreadLocalRandom.current().nextInt(minGold, maxGold + 1);

            if (player.hasBlessing("Wealth")) {
                goldEarned += 10;
            }

            player.addGold(goldEarned);
            JOptionPane.showMessageDialog(null, "You earned " + goldEarned + " gold!");

            BlessingEffectManager.onStageClear(player, stageNumber);

            stageIndex++;
            totalTurns += turnsUsed;

            new StatUpgradeGUI(() -> new RestOrContinueGUI(rested -> {
                if (rested) {
                    player.restoreHp();
                    totalTurns += 6;
                }
                nextStage();
            }));
        });
    }
}
