package main;

import javax.swing.*;
import java.awt.*;

public class EnterNameGUI extends JFrame {
    public EnterNameGUI() {
        setTitle("Enter Player Name");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Enter your name:", SwingConstants.CENTER);
        label.setFont(new Font("TH Sarabun New", Font.PLAIN, 22));

        JTextField nameField = new JTextField();
        JButton okButton = new JButton("OK");
        okButton.setFocusPainted(false);

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
}