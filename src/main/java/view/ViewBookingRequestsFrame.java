package view;

import controller.RideController;
import model.BookingRequest;
import model.Driver;
import model.Ride;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewBookingRequestsFrame extends JFrame {

    private RideController rideController;
    private Driver loggedInDriver;

    public ViewBookingRequestsFrame(RideController rideController, Driver loggedInDriver) {
        this.rideController = rideController;
        this.loggedInDriver = loggedInDriver;

        setTitle("Booking Requests");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);

        List<Ride> allRides = rideController.getAllRides();

        for (Ride ride : allRides) {
            if (ride.getDriverID() == loggedInDriver.getId()) {
                List<BookingRequest> requests = rideController.getRequestsForRide(ride);
                for (BookingRequest request : requests) {
                    JPanel requestPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    requestPanel.setBorder(BorderFactory.createTitledBorder("Ride to: " + ride.getDestination()));

                    JLabel info = new JLabel("Rider ID: " + request.getRiderID()
                            + " | Requested Seats: " + request.getRequestedSeats()
                            + " | Fare Offered: $" + request.getOfferedFare()
                            + " | Status: " + request.getStatus());

                    JButton approveButton = new JButton("Approve");
                    JButton rejectButton = new JButton("Reject");

                    approveButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            rideController.approveRequest(ride, request);
                            JOptionPane.showMessageDialog(ViewBookingRequestsFrame.this, "Request Approved");
                            dispose();
                            new ViewBookingRequestsFrame(rideController, loggedInDriver);
                        }
                    });

                    rejectButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            rideController.rejectRequest(ride, request);
                            JOptionPane.showMessageDialog(ViewBookingRequestsFrame.this, "Request Rejected");
                            dispose();
                            new ViewBookingRequestsFrame(rideController, loggedInDriver);
                        }
                    });

                    requestPanel.add(info);
                    requestPanel.add(approveButton);
                    requestPanel.add(rejectButton);
                    mainPanel.add(requestPanel);
                }
            }
        }

        setVisible(true);
    }
}
