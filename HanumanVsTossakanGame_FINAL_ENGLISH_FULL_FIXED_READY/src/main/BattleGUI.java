package main;

import battle.AttackerAction;
import battle.DefenderAction;
import battle.DamageCalculator;
import battle.BlessingPassiveEngine;
import battle.BlessingReactionHandler;
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
        setSize(900, 500);
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

        // Blessing panel
        BlessingDisplayPanel blessingPanel = new BlessingDisplayPanel(player);
        blessingPanel.setPreferredSize(new Dimension(300, 0));
        add(blessingPanel, BorderLayout.EAST);

        // Randomly decide who starts
        playerTurn = new Random().nextBoolean();
        appendLog("Turn " + turnCount + ": " + (playerTurn ? "You attack first!" : "Boss attacks first!"));

        BlessingPassiveEngine.onEveryTurn(player);  // passive blessing trigger
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
                        player.resetMomentum();
                    } else if (DamageCalculator.countered) {
                        appendLog("Boss countered your attack!");
                        player.takeDamage(-damage);
                        appendLog("You received " + (-damage) + " reflected damage!");
                        player.resetMomentum();
                    } else {
                        boss.takeDamage(damage);
                        appendLog("Dealt " + damage + " damage!");
                        player.incrementMomentum();

                        if (player.hasBlessing("Adrenaline")) {
                            player.restoreHpBy(10);
                            appendLog("Adrenaline: Restored 10 HP!");
                        }

                        if (player.hasBlessing("Golden Touch") && Math.random() < 0.25) {
                            player.addGold(5);
                            appendLog("Golden Touch: Gained +5 gold!");
                        }

                        if (player.hasBlessing("Combo Master") && Math.random() < 0.15) {
                            appendLog("Combo Master: You attack again!");
                            showActionOptions();
                            return;
                        }

                        if (player.hasBlessing("Finisher Chain") && action == AttackerAction.FINISHER && boss.isAlive()) {
                            if (Math.random() < 0.3) {
                                appendLog("Finisher Chain activated: Free extra turn!");
                                showActionOptions();
                                return;
                            }
                        }
                    }

                    checkBattleEnd();
                    if (boss.isAlive()) {
                        playerTurn = false;
                        turnCount++;
                        appendLog("---");
                        appendLog("Turn " + turnCount + ": Defend against " + boss.getName());
                        BlessingPassiveEngine.onEveryTurn(player);
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
                        if (player.hasBlessing("Trickster’s Luck")) {
                            player.addSpeed(2);
                            appendLog("Trickster’s Luck: Gained +2 SPD!");
                        }
                    } else if (DamageCalculator.countered) {
                        appendLog("You countered the attack!");
                        boss.takeDamage(-damage);
                        appendLog(boss.getName() + " received " + (-damage) + " reflected damage!");
                        if (player.hasBlessing("Counter Master")) {
                            int extra = (int) (-damage * 0.5);
                            boss.takeDamage(extra);
                            appendLog("Counter Master: Extra " + extra + " damage reflected!");
                        }
                    } else {
                        player.takeDamage(damage);
                        appendLog("You received " + damage + " damage!");

                        if (damage > 20 && player.hasBlessing("Revenge")) {
                            player.setRevengeActive(true);
                            appendLog("Revenge activated: Your next attack will be stronger!");
                        }
                    }

                    checkBattleEnd();
                    if (player.isAlive()) {
                        playerTurn = true;
                        turnCount++;
                        appendLog("---");
                        appendLog("Turn " + turnCount + ": Your turn to attack!");
                        BlessingPassiveEngine.onEveryTurn(player);
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
                "<br>ATK: " + player.getAttack() + "<br>DEF: " + player.getDefense() + "<br>SPD: " + player.getSpeed() + "<br>GOLD: " + player.getGold() + "</html>");
        bossStats.setText("<html><b>" + boss.getName() + "</b><br>HP: " + boss.getHp() + "/" + boss.getMaxHp() +
                "<br>ATK: " + boss.getAttack() + "<br>DEF: " + boss.getDefense() + "<br>SPD: " + boss.getSpeed() + "</html>");
    }

    private void checkBattleEnd() {
        if (!player.isAlive()) {
            boolean revived = BlessingReactionHandler.tryRevive(player);
            if (revived) {
                appendLog("Blessing of Revival activated: You revived with 50 HP!");
                updateStats();
                return;
            }

            boolean survived = BlessingReactionHandler.tryLastStand(player);
            if (survived) {
                appendLog("Last Stand activated! You survived at 1 HP!");
                updateStats();
                return;
            }

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