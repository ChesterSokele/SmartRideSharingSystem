package view;

import model.Ride;
import model.Rider;  // Changed from BasicUser to Rider
import javax.swing.*;
import java.awt.*;

public class RiderDashboardFrame extends JFrame {

    private void showMyBookings(Rider rider) {
        // Implement booking viewing logic here
        JOptionPane.showMessageDialog(this, "My Bookings feature coming soon!");
    }

    // Change parameter type from BasicUser to Rider
    public RiderDashboardFrame(Rider rider) {
        setTitle("Rider Dashboard - " + rider.getName());  // Now you can access rider.getName()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JButton viewAvailableRidesButton = new JButton("View Available Rides");
        JButton requestRideButton = new JButton("Request a Ride");
        JButton myBookingsButton = new JButton("My Bookings");
        JButton logoutButton = new JButton("Logout");

        // Example action listener using the rider object
        viewAvailableRidesButton.addActionListener(e -> {
            new ViewAvailableRidesFrame(rider);
            dispose();
        });

        // In RiderDashboardFrame, update requestRideButton:
        requestRideButton.addActionListener(e -> {
            // First show available rides, then they can select one to request
            new ViewAvailableRidesFrame(rider);
            dispose();
        });

        // Then update the button action:
        myBookingsButton.addActionListener(e -> {
            new MyBookingsFrame(rider);
            dispose();
        });

        logoutButton.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        panel.add(viewAvailableRidesButton);
        panel.add(requestRideButton);
        panel.add(myBookingsButton);
        panel.add(logoutButton);

        add(panel);
    }
}