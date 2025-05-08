package view;

import controller.MainController;
import model.Ride;
import model.Rider;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RequestRideFrame extends JFrame {
    private final Rider rider;
    private final Ride selectedRide;

    public RequestRideFrame(Rider rider, Ride selectedRide) {
        this.rider = rider;
        this.selectedRide = selectedRide;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Request Ride to " + selectedRide.getDestination());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));



        // Request Form Panel
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Request Details"));

        JLabel seatsLabel = new JLabel("Number of Seats:");
        JSpinner seatsSpinner = new JSpinner(
                new SpinnerNumberModel(1, 1, selectedRide.getAvailableSeats(), 1));

        JLabel pickupLabel = new JLabel("Pickup Point:");
        JTextField pickupField = new JTextField(selectedRide.getPickupPoint());

        JLabel fareLabel = new JLabel("Total Fare:");
        JLabel fareValue = new JLabel("$" + selectedRide.getFarePerSeat());

        // Update fare when seats change
        seatsSpinner.addChangeListener(e -> {
            int seats = (Integer) seatsSpinner.getValue();
            double totalFare = seats * selectedRide.getFarePerSeat();
            fareValue.setText("$" + String.format("%.2f", totalFare));
        });

        formPanel.add(seatsLabel);
        formPanel.add(seatsSpinner);
        formPanel.add(pickupLabel);
        formPanel.add(pickupField);
        formPanel.add(fareLabel);
        formPanel.add(fareValue);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton requestButton = new JButton("Submit Request");
        JButton cancelButton = new JButton("Cancel");

        requestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitRequest(
                        (Integer) seatsSpinner.getValue(),
                        pickupField.getText()
                );
            }
        });


        cancelButton.addActionListener(e -> {
            new ViewAvailableRidesFrame(rider);
            dispose();
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(requestButton);

        // Assemble main panel
        //mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void submitRequest(int seats, String pickupPoint) {
        double totalFare = seats * selectedRide.getFarePerSeat();

        boolean success = MainController.rideController.requestToJoinRide(
                selectedRide,
                rider.getUserID(),
                seats,
                totalFare,
                pickupPoint
        );

        if (success) {
            JOptionPane.showMessageDialog(this,
                    "Ride request submitted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new RiderDashboardFrame(rider); // Return to dashboard
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to submit ride request. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}