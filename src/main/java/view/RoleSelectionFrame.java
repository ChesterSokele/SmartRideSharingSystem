package view;

import controller.RideController;
import model.Driver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static controller.MainController.rideController;

public class RoleSelectionFrame extends JFrame {

    public RoleSelectionFrame() {
        setTitle("Smart Ride-Sharing System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Select Your Role");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton riderButton = new JButton("Rider");
        JButton driverButton = new JButton("Driver");

        riderButton.setFont(new Font("Arial", Font.PLAIN, 16));
        driverButton.setFont(new Font("Arial", Font.PLAIN, 16));

        riderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RiderDashboardFrame().setVisible(true);
            }
        });

        driverButton.addActionListener(e -> {
            // Create a dummy driver for now
            Driver driver = new Driver(
                    1,
                    "Driver 1",               // name
                    "driver@email.com",      // email
                    "0812345678",            // phone
                    "LIC12345",              // license number
                    2                        // experience
            );

            RideController rideController = new RideController();

            DriverDashboardFrame driverFrame = new DriverDashboardFrame(driver, rideController);
            driverFrame.setVisible(true);
            dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 20, 10));
        buttonPanel.add(riderButton);
        buttonPanel.add(driverButton);

        setLayout(new BorderLayout(20, 20));
        add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RoleSelectionFrame().setVisible(true);
        });
    }
}
