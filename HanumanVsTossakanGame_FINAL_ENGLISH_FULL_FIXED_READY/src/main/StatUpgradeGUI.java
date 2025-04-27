
package main;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class StatUpgradeGUI extends JFrame {
    public StatUpgradeGUI(Runnable callback) {
        setTitle("Upgrade Your Stats");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Upgrade your stats", SwingConstants.CENTER);
        label.setFont(new Font("TH Sarabun New", Font.BOLD, 24));
        add(label, BorderLayout.NORTH);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            dispose();
            callback.run();
        });

        JPanel panel = new JPanel();
        panel.add(confirmButton);
        add(panel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
