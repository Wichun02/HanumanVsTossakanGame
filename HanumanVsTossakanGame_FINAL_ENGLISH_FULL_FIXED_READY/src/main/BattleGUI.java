package main;

import battle.AttackerAction;
import battle.DefenderAction;
import battle.DamageCalculator;
import character.Boss;
import character.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.function.IntConsumer;

public class BattleGUI extends JFrame {
    private final Player player;
    private final Boss boss;
    private final JTextArea battleLog;
    private final JLabel playerStats;
    private final JLabel bossStats;
    private final IntConsumer battleEndCallback;

    private boolean playerTurn; // true = player attack, false = player defend
    private int turnCount = 1;

    private final JPanel actionPanel = new JPanel(new GridLayout(2, 3));

    public BattleGUI(Player player, Boss boss, IntConsumer callback) {
        this.player = player;
        this.boss = boss;
        this.battleEndCallback = callback;

        setTitle("Battle: " + player.getName() + " VS " + boss.getName());
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Stats panel
        playerStats = new JLabel();
        bossStats = new JLabel();
        updateStats();

        JPanel statsPanel = new JPanel(new GridLayout(1, 2));
        statsPanel.add(playerStats);
        statsPanel.add(bossStats);
        add(statsPanel, BorderLayout.NORTH);

        // Battle log
        battleLog = new JTextArea();
        battleLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(battleLog);
        add(scrollPane, BorderLayout.CENTER);

        // Action panel
        add(actionPanel, BorderLayout.SOUTH);

        // Randomly decide who starts
        playerTurn = new Random().nextBoolean();
        appendLog("Turn " + turnCount + ": " + (playerTurn ? "You attack first!" : "Boss attacks first!"));
        showActionOptions();

        setVisible(true);
    }

    private void showActionOptions() {
        actionPanel.removeAll();
        if (playerTurn) {
            for (AttackerAction action : AttackerAction.values()) {
                JButton btn = new JButton(action.name());
                btn.addActionListener(e -> {
                    player.setQueuedAttack(action);
                    DefenderAction bossDefense = boss.chooseDefendAction();
                    int damage = DamageCalculator.calculate(player, boss, action, bossDefense);

                    appendLog("You used " + action + " | " + boss.getName() + " defended with " + bossDefense);

                    if (DamageCalculator.dodged) {
                        appendLog(boss.getName() + " dodged your attack!");
                    } else if (DamageCalculator.countered) {
                        appendLog("Boss countered your attack!");
                        player.takeDamage(-damage); // reflected
                        appendLog("You received " + (-damage) + " reflected damage!");
                    } else {
                        boss.takeDamage(damage);
                        appendLog("Dealt " + damage + " damage!");
                    }

                    checkBattleEnd();
                    if (boss.isAlive()) {
                        playerTurn = false;
                        turnCount++;
                        appendLog("---");
                        appendLog("Turn " + turnCount + ": Defend against " + boss.getName());
                        showActionOptions();
                    }
                });
                actionPanel.add(btn);
            }
        } else {
            for (DefenderAction action : DefenderAction.values()) {
                JButton btn = new JButton(action.name());
                btn.addActionListener(e -> {
                    player.setQueuedDefense(action);
                    AttackerAction bossAttack = boss.chooseAttackAction();
                    int damage = DamageCalculator.calculate(boss, player, bossAttack, action);

                    appendLog(boss.getName() + " used " + bossAttack + " | You defended with " + action);

                    if (DamageCalculator.dodged) {
                        appendLog("You dodged the attack!");
                    } else if (DamageCalculator.countered) {
                        appendLog("You countered the attack!");
                        boss.takeDamage(-damage); // reflected
                        appendLog(boss.getName() + " received " + (-damage) + " reflected damage!");
                    } else {
                        player.takeDamage(damage);
                        appendLog("You received " + damage + " damage!");
                    }

                    checkBattleEnd();
                    if (player.isAlive()) {
                        playerTurn = true;
                        turnCount++;
                        appendLog("---");
                        appendLog("Turn " + turnCount + ": Your turn to attack!");
                        showActionOptions();
                    }
                });
                actionPanel.add(btn);
            }
        }
        actionPanel.revalidate();
        actionPanel.repaint();
        updateStats();
    }

    private void updateStats() {
        playerStats.setText("<html><b>" + player.getName() + "</b><br>HP: " + player.getHp() + "/" + player.getMaxHp() +
                "<br>ATK: " + player.getAttack() + "<br>DEF: " + player.getDefense() + "<br>SPD: " + player.getSpeed() + "</html>");
        bossStats.setText("<html><b>" + boss.getName() + "</b><br>HP: " + boss.getHp() + "/" + boss.getMaxHp() +
                "<br>ATK: " + boss.getAttack() + "<br>DEF: " + boss.getDefense() + "<br>SPD: " + boss.getSpeed() + "</html>");
    }

    private void checkBattleEnd() {
        if (!player.isAlive()) {
            appendLog("You have been defeated!");
            dispose();
            battleEndCallback.accept(turnCount);
        } else if (!boss.isAlive()) {
            appendLog("You defeated " + boss.getName() + "!");
            JOptionPane.showMessageDialog(this, "Victory! Proceed to next stage.");
            dispose();
            battleEndCallback.accept(turnCount);
        }
    }

    private void appendLog(String message) {
        battleLog.append(message + "\n");
    }
}