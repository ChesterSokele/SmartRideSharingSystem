package view;

import controller.MainController;
import model.Ride;
import model.Rider;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewAvailableRidesFrame extends JFrame {
    private final Rider rider;

    public ViewAvailableRidesFrame(Rider rider) {
        this.rider = rider;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Available Rides - " + rider.getName());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel ridesPanel = new JPanel();
        ridesPanel.setLayout(new BoxLayout(ridesPanel, BoxLayout.Y_AXIS));

        // Get available rides from controller
        List<Ride> availableRides = MainController.rideController.getAvailableRides();

        if (availableRides.isEmpty()) {
            ridesPanel.add(new JLabel("No available rides at the moment."));
        } else {
            for (Ride ride : availableRides) {
                ridesPanel.add(createRidePanel(ride));
            }
        }

        JScrollPane scrollPane = new JScrollPane(ridesPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> {
            new RiderDashboardFrame(rider);
            dispose();
        });
        mainPanel.add(backButton, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createRidePanel(Ride ride) {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
                "Ride to " + ride.getDestination() +
                        " | Seats: " + ride.getAvailableSeats() +
                        " | Fare: $" + ride.getFarePerSeat() + "/seat"
        ));

        JLabel driverLabel = new JLabel("Driver ID: " + ride.getDriverID());
        JLabel pickupLabel = new JLabel("Pickup: " + ride.getPickupPoint());
        JLabel timeLabel = new JLabel("Posted: " + ride.getTimeLogged());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton requestButton = new JButton("Request Ride");

        requestButton.addActionListener(e -> {
            new RequestRideFrame(rider, ride);  // Pass both rider and selected ride
            dispose();
        });

        buttonPanel.add(requestButton);

        panel.add(driverLabel);
        panel.add(pickupLabel);
        panel.add(timeLabel);
        panel.add(buttonPanel);

        return panel;
    }

    private void showRequestDialog(Ride ride) {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));

        JSpinner seatsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, ride.getAvailableSeats(), 1));
        JTextField pickupField = new JTextField(ride.getPickupPoint());
        JLabel fareLabel = new JLabel("Total fare: $" + ride.getFarePerSeat());

        ((JSpinner.DefaultEditor) seatsSpinner.getEditor()).getTextField().setEditable(false);
        seatsSpinner.addChangeListener(e -> {
            int seats = (int) seatsSpinner.getValue();
            fareLabel.setText("Total fare: $" + (seats * ride.getFarePerSeat()));
        });

        panel.add(new JLabel("Seats:"));
        panel.add(seatsSpinner);
        panel.add(new JLabel("Pickup Point:"));
        panel.add(pickupField);
        panel.add(fareLabel);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Request Ride to " + ride.getDestination(),
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            int seats = (int) seatsSpinner.getValue();
            String pickup = pickupField.getText();
            double totalFare = seats * ride.getFarePerSeat();

            // Create and send booking request
            boolean success = MainController.rideController.requestToJoinRide(
                    ride,
                    rider.getUserID(),
                    seats,
                    totalFare,
                    pickup
            );

            if (success) {
                JOptionPane.showMessageDialog(this, "Ride request submitted!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to submit request", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }
}