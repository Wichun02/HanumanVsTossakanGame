package main;

import character.Player;
import character.Boss;
import character.EnemyStage;
import battle.AttackerAction;
import battle.DefenderAction;

import javax.swing.*;
import java.util.List;

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
        if (stageIndex >= enemies.size()) {
            ScoreManager.showFinalScore(player, stageIndex, totalTurns);
            return;
        }

        Boss currentEnemy = enemies.get(stageIndex);
        BattleGUI battle = new BattleGUI(player, currentEnemy);
        battle.setBattleEndCallback(turnsUsed -> {
            if (!player.isAlive()) {
                JOptionPane.showMessageDialog(null, "Game Over! You lost at stage " + (stageIndex + 1));
                ScoreManager.showFinalScore(player, stageIndex, totalTurns);
                return;
            }

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
