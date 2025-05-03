
package main;

import character.Player;
import character.Boss;
import javax.swing.*;
import java.awt.*;
import java.util.function.IntConsumer;

public class BattleGUI extends JFrame {
    private IntConsumer battleEndCallback;

    // Constructor for entering name (original)
    public BattleGUI() {
        setTitle("Enter Player Name");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Enter your name:", SwingConstants.CENTER);
        label.setFont(new Font("TH Sarabun New", Font.PLAIN, 22));

        JTextField nameField = new JTextField();
        JButton okButton = new JButton("OK");

        okButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                dispose();
                GameFlowManager.startGame(name);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a name");
            }
        });

        setLayout(new GridLayout(3, 1, 10, 10));
        add(label);
        add(nameField);
        add(okButton);
        setVisible(true);
    }

    // ✅ Constructor for battle
    public BattleGUI(Player player, Boss boss) {
        setTitle("Battle: " + player.getName() + " VS " + boss.getName());
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea battleLog = new JTextArea();
        battleLog.setEditable(false);

        JButton endTurnButton = new JButton("End Turn");
        endTurnButton.addActionListener(e -> {
            int turnsUsed = 3; // ตัวอย่างจำนวนเทิร์น
            if (battleEndCallback != null) {
                battleEndCallback.accept(turnsUsed);
            }
            dispose();
        });

        add(new JScrollPane(battleLog), BorderLayout.CENTER);
        add(endTurnButton, BorderLayout.SOUTH);
        setVisible(true);
    }

    public void setBattleEndCallback(IntConsumer callback) {
        this.battleEndCallback = callback;
    }
}
