package view;

import javax.swing.*;
import java.awt.*;

public class RiderDashboardFrame extends JFrame {

    public RiderDashboardFrame() {
        setTitle("Rider Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JButton viewAvailableRidesButton = new JButton("View Available Rides");
        JButton requestRideButton = new JButton("Request a Ride");
        JButton myBookingsButton = new JButton("My Bookings");
        JButton logoutButton = new JButton("Logout");

        panel.add(viewAvailableRidesButton);
        panel.add(requestRideButton);
        panel.add(myBookingsButton);
        panel.add(logoutButton);

        add(panel);
    }
}
