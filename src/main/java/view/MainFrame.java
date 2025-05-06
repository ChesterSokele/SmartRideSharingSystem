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
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JButton viewRidesButton = new JButton("View Available Rides");
        JButton requestRideButton = new JButton("Request a Ride");
        JButton myBookingsButton = new JButton("My Bookings");
        JButton logoutButton = new JButton("Logout");

        // Add action listeners for rider buttons
        viewRidesButton.addActionListener(e -> {
            new ViewAvailableRidesFrame(rider);
            dispose();
        });


        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        panel.add(viewRidesButton);
        panel.add(requestRideButton);
        panel.add(myBookingsButton);
        panel.add(logoutButton);

        add(panel);
        setVisible(true);
    }
}