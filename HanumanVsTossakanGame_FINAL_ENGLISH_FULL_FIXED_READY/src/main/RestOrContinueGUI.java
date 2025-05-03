
package main;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class RestOrContinueGUI extends JFrame {
    public RestOrContinueGUI(Consumer<Boolean> callback) {
        setTitle("Rest or Continue");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        JButton restButton = new JButton("Rest (Restore Full HP)");
        JButton continueButton = new JButton("Continue");

        restButton.addActionListener(e -> {
            dispose();
            callback.accept(true);
        });

        continueButton.addActionListener(e -> {
            dispose();
            callback.accept(false);
        });

        add(restButton);
        add(continueButton);

        setVisible(true);
    }
}
