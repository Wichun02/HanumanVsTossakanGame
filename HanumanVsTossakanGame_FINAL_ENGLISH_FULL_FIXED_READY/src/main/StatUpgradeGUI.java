package main;

import character.Player;

import javax.swing.*;
import java.awt.*;

public class StatUpgradeGUI extends JFrame {
    private int upgradesLeft = 4;
    private final Player player;
    private final Runnable callback;
    private final JLabel counterLabel;

    public StatUpgradeGUI(Player player, Runnable callback) {
        this.player = player;
        this.callback = callback;

        setTitle("Upgrade Your Stats");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Choose 4 upgrades", SwingConstants.CENTER);
        label.setFont(new Font("TH Sarabun New", Font.BOLD, 24));
        add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        addUpgradeButton(buttonPanel, "ATK");
        addUpgradeButton(buttonPanel, "DEF");
        addUpgradeButton(buttonPanel, "SPD");
        addUpgradeButton(buttonPanel, "HP");
        add(buttonPanel, BorderLayout.CENTER);

        counterLabel = new JLabel("Upgrades left: 4", SwingConstants.CENTER);
        counterLabel.setFont(new Font("TH Sarabun New", Font.PLAIN, 20));
        add(counterLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addUpgradeButton(JPanel panel, String statName) {
        JButton btn = new JButton(statName + " +1");
        btn.addActionListener(e -> handleUpgrade(statName));
        panel.add(btn);
    }

    private void handleUpgrade(String stat) {
        if (upgradesLeft <= 0) return;

        player.upgrade(stat);
        upgradesLeft--;
        counterLabel.setText("Upgrades left: " + upgradesLeft);

        if (upgradesLeft == 0) {
            JOptionPane.showMessageDialog(this, "Upgrade complete!");
            dispose();
            callback.run();
        }
    }
}