package org.example;

import view.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Launch the GUI
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
