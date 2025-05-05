package main;

import javax.swing.*;
import java.awt.*;

public class MainMenuGUI extends JFrame {
    public MainMenuGUI() {
        setTitle("Hanuman vs Tossakan - Main Menu");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 🔹 โหลดภาพพื้นหลัง
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/images/menu_title.png"));
        JLabel background = new JLabel(bgIcon);
        setContentPane(background);  // ใส่พื้นหลังแทน panel ปกติ
        background.setLayout(new GridLayout(3, 1, 10, 10));  // ใช้ layout กับพื้นหลังได้

        // 🔹 ข้อความชื่อเกม
        JLabel title = new JLabel("Hanuman vs Tossakan", SwingConstants.CENTER);
        title.setFont(new Font("TH Sarabun New", Font.BOLD, 36));
        title.setForeground(Color.WHITE); // สีอักษรให้ตัดกับพื้นหลัง

        // 🔹 ปุ่มเริ่มเกม
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> {
            dispose();
            new EnterNameGUI();
        });

        // 🔹 ปุ่มออกจากเกม
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        // 🔹 ทำปุ่มให้โปร่งใส (ไม่มีกรอบ ไม่มีพื้นหลัง)
        makeButtonTransparent(startButton);
        makeButtonTransparent(exitButton);

        // 🔹 เพิ่มลงในพื้นหลัง
        background.add(title);
        background.add(startButton);
        background.add(exitButton);

        setVisible(true);
    }

    // 🪄 ทำให้ปุ่มไม่มีพื้นหลัง ไม่มีกรอบ
    private void makeButtonTransparent(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("TH Sarabun New", Font.BOLD, 24));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenuGUI::new);
    }
}
