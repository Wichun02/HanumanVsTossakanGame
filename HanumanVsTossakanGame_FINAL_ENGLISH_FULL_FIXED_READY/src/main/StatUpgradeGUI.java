package main;

import character.Player;

import javax.swing.*;
import java.awt.*;

public class StatUpgradeGUI extends JFrame {
    private final JLabel statusLabel;
    private int count = 0;
    private int maxUpgrade;

    public StatUpgradeGUI(Runnable callback) {
        setTitle("Choose Your Stat Upgrades");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Player player = GameFlowManager.player;
        this.maxUpgrade = player.hasBlessing("Trainer’s Insight") ? 5 : 4;

        JPanel panel = new JPanel(new GridLayout(6, 1));
        JLabel label = new JLabel("Choose your upgrades (ATK +1, DEF +1, SPD +1, HP +10):", SwingConstants.CENTER);
        statusLabel = new JLabel(getStatusText(), SwingConstants.CENTER);

        JButton atk = new JButton("Upgrade ATK");
        atk.addActionListener(e -> {
            player.upgrade("atk");
            checkFinish(callback);
        });

        JButton def = new JButton("Upgrade DEF");
        def.addActionListener(e -> {
            player.upgrade("def");
            checkFinish(callback);
        });

        JButton spd = new JButton("Upgrade SPD");
        spd.addActionListener(e -> {
            player.upgrade("spd");
            checkFinish(callback);
        });

        JButton hp = new JButton("Upgrade HP");
        hp.addActionListener(e -> {
            player.upgrade("hp");
            checkFinish(callback);
        });

        panel.add(label);
        panel.add(statusLabel);
        panel.add(atk);
        panel.add(def);
        panel.add(spd);
        panel.add(hp);

        add(panel);
        setVisible(true);
    }

    private String getStatusText() {
        return "Upgrades used: " + count + " / " + maxUpgrade;
    }

    private void checkFinish(Runnable callback) {
        count++;
        statusLabel.setText(getStatusText());
        if (count >= maxUpgrade) {
            dispose();
            callback.run();
        }
    }
}
