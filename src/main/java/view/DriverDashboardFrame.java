package view;

import controller.MainController;
import controller.RideController;
import model.Driver;

import javax.swing.*;
import java.awt.*;

public class DriverDashboardFrame extends JFrame {
    private final Driver driver;
    private final RideController rideController;

    public DriverDashboardFrame(Driver driver, RideController rideController) {
        this.driver = driver;
        this.rideController = rideController;

        setTitle("Driver Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton listRideButton = new JButton("📝 List a Ride");
        JButton viewRequestsButton = new JButton("📄 View Ride Requests");
        JButton logoutButton = new JButton("Logout");

        listRideButton.addActionListener(e -> {
            new ListRideFrame(driver);  // ✅ fixed: pass both
        });

        // In DriverDashboardFrame, update viewRequestsButton action:
        viewRequestsButton.addActionListener(e -> {
            new ViewBookingRequestsFrame(MainController.rideController, driver);
            dispose();
        });

        logoutButton.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        panel.add(listRideButton);
        panel.add(viewRequestsButton);
        panel.add(logoutButton);


        add(panel);
        setVisible(true);
    }
}
