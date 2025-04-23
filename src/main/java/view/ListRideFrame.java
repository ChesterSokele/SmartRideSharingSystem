package view;

import controller.MainController;
import model.Driver;

import javax.swing.*;
import java.awt.*;

public class ListRideFrame extends JFrame {

    public ListRideFrame(Driver driver) {
        setTitle("List a Ride");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        JLabel pickupLabel = new JLabel("Pickup Point:");
        JTextField pickupField = new JTextField();

        JLabel destinationLabel = new JLabel("Destination:");
        JTextField destinationField = new JTextField();

        JLabel seatsLabel = new JLabel("Available Seats:");
        JTextField seatsField = new JTextField();

        JLabel fareLabel = new JLabel("Fare (per person):");
        JTextField fareField = new JTextField();

        JButton listButton = new JButton("List Ride");

        listButton.addActionListener(e -> {
            String pickup = pickupField.getText();
            String destination = destinationField.getText();
            int seats;
            double fare;

            try {
                seats = Integer.parseInt(seatsField.getText());
                fare = Double.parseDouble(fareField.getText());

                MainController.rideController.createRide(driver, pickup, destination, seats, fare);

                JOptionPane.showMessageDialog(this, "✅ Ride listed successfully!");
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❌ Please enter valid numbers for seats and fare.");
            }
        });

        panel.add(pickupLabel);
        panel.add(pickupField);

        panel.add(destinationLabel);
        panel.add(destinationField);

        panel.add(seatsLabel);
        panel.add(seatsField);

        panel.add(fareLabel);
        panel.add(fareField);

        panel.add(new JLabel());  // empty cell
        panel.add(listButton);

        add(panel);
        setVisible(true);
    }
}
