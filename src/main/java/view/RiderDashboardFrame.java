package view;

// Import model.BasicUser; // This import seems to be from an older version or typo, should be Rider
import model.Rider; // Correct import
import javax.swing.*;
import java.awt.*;

public class RiderDashboardFrame extends JFrame {

    // This method seems out of place or a remnant.
    // If MyBookingsFrame handles showing bookings, this might not be needed here.
    private void showMyBookings(Rider rider) {
        // new MyBookingsFrame(rider); // This would be the typical action
        // dispose();
        JOptionPane.showMessageDialog(this, "My Bookings feature is handled by 'My Bookings' button.");
    }

    // Constructor takes a Rider object
    public RiderDashboardFrame(Rider rider) {
        setTitle("Rider Dashboard - " + rider.getName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Or DISPOSE_ON_CLOSE if LoginFrame is main exit
        setSize(400, 350); // Slightly increased height for better layout
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); // Use BorderLayout for better structure

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

        // Styling buttons
        Font buttonFont = new Font("Arial", Font.PLAIN, 14);
        viewAvailableRidesButton.setFont(buttonFont);
        requestRideButton.setFont(buttonFont);
        myBookingsButton.setFont(buttonFont);
        logoutButton.setFont(buttonFont);

        // Action listener for viewing available rides
        viewAvailableRidesButton.addActionListener(e -> {
            new ViewAvailableRidesFrame(rider); // Pass the Rider object
            dispose(); // Close current dashboard
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

        add(buttonPanel, BorderLayout.CENTER); // Add button panel to the center

        // --- THIS IS THE IMPORTANT FIX FOR VISIBILITY ---
        setVisible(true);
        // --- END OF VISIBILITY FIX ---
    }

}