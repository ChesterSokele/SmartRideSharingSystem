// MainFrame.java
package view;

import controller.MainController;
import model.Driver;
import model.Rider;
import model.User;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(User user) {
        setTitle("Smart Ride Sharing System - " + user.getName());
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        if (user instanceof Driver) {
            showDriverDashboard((Driver) user);
        } else if (user instanceof Rider) {
            showRiderDashboard((Rider) user);
        }
    }

    private void showDriverDashboard(Driver driver) {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        JButton listRideButton = new JButton("List New Ride");
        JButton viewRequestsButton = new JButton("View Booking Requests");
        JButton logoutButton = new JButton("Logout");

        listRideButton.addActionListener(e -> {
            new ListRideFrame(driver);
            dispose();
        });

        viewRequestsButton.addActionListener(e -> {
            new ViewBookingRequestsFrame(MainController.rideController, driver);
            dispose();
        });


        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        panel.add(listRideButton);
        panel.add(viewRequestsButton);
        panel.add(logoutButton);

        add(panel);
        setVisible(true);
    }

    private void showRiderDashboard(Rider rider) {

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome, " + rider.getName() + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(welcomeLabel, BorderLayout.NORTH);

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10)); // Rows, Cols, Hgap, Vgap
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding

        JButton viewAvailableRidesButton = new JButton("View Available Rides");
        JButton requestRideButton = new JButton("Request a Ride (View Available First)"); // Clarified button text
        JButton myBookingsButton = new JButton("My Bookings");
        JButton logoutButton = new JButton("Logout");

        // Add action listeners for rider buttons
        viewAvailableRidesButton.addActionListener(e -> {
            new ViewAvailableRidesFrame(rider);
            dispose();
        });

        // Action listener for requesting a ride (typically shows available rides first)
        requestRideButton.addActionListener(e -> {
            new ViewAvailableRidesFrame(rider); // User selects a ride from this frame to request
            dispose();
        });

        // Action listener for viewing "My Bookings"
        myBookingsButton.addActionListener(e -> {
            new MyBookingsFrame(rider); // Pass the Rider object
            dispose(); // Close current dashboard
        });

        // Action listener for logout
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true); // Make sure LoginFrame is set visible
            dispose(); // Close current dashboard
        });



        // Add buttons to the panel
        buttonPanel.add(viewAvailableRidesButton);
        buttonPanel.add(requestRideButton); // Consider if this button's functionality is distinct enough
        buttonPanel.add(myBookingsButton);
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}