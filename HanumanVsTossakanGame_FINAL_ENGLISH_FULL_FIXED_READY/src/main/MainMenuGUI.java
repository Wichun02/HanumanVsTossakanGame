package main;

import javax.swing.*;
import java.awt.*;

public class MainMenuGUI extends JFrame {
    public MainMenuGUI() {
        setTitle("Hanuman vs Tossakan - Main Menu");
        setSize(1536, 1024); // ขนาดตรงกับภาพ
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // พื้นหลัง
        ImageIcon bgIcon = new ImageIcon("assets/menu_title.png");
        JLabel background = new JLabel(bgIcon);
        background.setBounds(0, 0, 1536, 1024);

        // สร้าง panel สำหรับปุ่ม
        JPanel buttonPanel = new JPanel(null);
        buttonPanel.setOpaque(false);
        buttonPanel.setBounds(0, 0, 1536, 1024);

        // ปุ่ม Start Game
        JButton startButton = new JButton();
        startButton.setBounds(500, 550, 500, 80); // << ปรับให้ครอบข้อความ "START GAME"
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> {
            dispose();
            new EnterNameGUI();
        });

        // ปุ่ม Exit
        JButton exitButton = new JButton();
        exitButton.setBounds(650, 660, 170, 80); // << ปรับให้ตรงกับ "EXIT"
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> System.exit(0));

        // เพิ่มปุ่มเข้า panel
        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);

        // ใส่ทั้งหมดใน layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1536, 1024));
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER);

        setContentPane(layeredPane);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenuGUI::new);
    }
}
