package view;

import controller.MainController;
import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    public RegisterFrame() {
        setTitle("Register New User");
        setSize(400, 300);  // Slightly wider for better spacing
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form panel with grid layout
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 10));

        // Components
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JLabel phoneLabel = new JLabel("Phone Number:");
        JTextField phoneField = new JTextField();

        JLabel roleLabel = new JLabel("Role:");
        String[] roles = {"Driver", "Rider"};
        JComboBox<String> roleCombo = new JComboBox<>(roles);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton backButton = new JButton("Back");
        JButton registerButton = new JButton("Register");

        // Add components to form panel in logical order
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);
        formPanel.add(roleLabel);
        formPanel.add(roleCombo);

        // Add empty cells for button alignment
        formPanel.add(new JLabel());
        formPanel.add(new JLabel());

        // Add buttons to button panel
        buttonPanel.add(backButton);
        buttonPanel.add(registerButton);

        // Add action listeners
        registerButton.addActionListener(e -> handleRegistration(
                usernameField.getText(),
                emailField.getText(),
                new String(passwordField.getPassword()),
                phoneField.getText(),
                (String) roleCombo.getSelectedItem()
        ));

        backButton.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        // Assemble main panel
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        setVisible(true);
    }

    private void handleRegistration(String username, String email, String password,
                                    String phone, String role) {
        boolean success = MainController.userController.registerUser(
                username, email, password, phone, role
        );

        if (success) {
            JOptionPane.showMessageDialog(this,
                    "✅ Registered successfully! You can now log in.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            new LoginFrame();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "❌ Registration failed. Email might already exist.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}