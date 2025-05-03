package main;

import javax.swing.*;
import java.awt.*;

public class MainMenuGUI extends JFrame {
    public MainMenuGUI() {
        setTitle("Hanuman vs Tossakan - Main Menu");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Hanuman vs Tossakan", SwingConstants.CENTER);
        title.setFont(new Font("TH Sarabun New", Font.BOLD, 28));

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> {
            dispose();
            new EnterNameGUI();
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.add(title);
        panel.add(startButton);
        panel.add(exitButton);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenuGUI::new);
    }
}
