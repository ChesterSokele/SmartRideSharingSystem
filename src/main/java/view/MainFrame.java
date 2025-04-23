package view;

import controller.RideController;
import model.Driver;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Smart Ride Sharing System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton riderButton = new JButton("Login as Rider");
        JButton driverButton = new JButton("Login as Driver");

        // Rider login
        riderButton.addActionListener(e -> {
            RiderDashboardFrame riderFrame = new RiderDashboardFrame();
            riderFrame.setVisible(true);
            dispose();
        });

        // ✅ Driver login — pass a Driver object
        driverButton.addActionListener(e -> {
            // dummy driver for now
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

        JPanel panel = new JPanel();
        panel.add(riderButton);
        panel.add(driverButton);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }
}
