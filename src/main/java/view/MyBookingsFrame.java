package view;

import controller.MainController;
import dao.BookingRequestDAO;
import model.BookingRequest;
import model.Rider;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MyBookingsFrame extends JFrame {
    public MyBookingsFrame(Rider rider) {
        setTitle("My Bookings - " + rider.getName());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel bookingsPanel = new JPanel();
        bookingsPanel.setLayout(new BoxLayout(bookingsPanel, BoxLayout.Y_AXIS));

        // Get bookings from database
        List<BookingRequest> bookings = BookingRequestDAO.getRiderBookings(rider.getUserID());

        if (bookings.isEmpty()) {
            bookingsPanel.add(new JLabel("You have no bookings yet", SwingConstants.CENTER));
        } else {
            for (BookingRequest booking : bookings) {
                bookingsPanel.add(createBookingPanel(booking));
            }
        }

        JScrollPane scrollPane = new JScrollPane(bookingsPanel);
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

    private JPanel createBookingPanel(BookingRequest booking) {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
                "Booking #" + booking.getRideID() +
                        " - Status: " + booking.getStatus()
        ));

        panel.add(new JLabel("From: " + booking.getPickupPoint()));
        panel.add(new JLabel("To: " + booking.getDestination()));
        panel.add(new JLabel("Seats: " + booking.getRequestedSeats()));
        panel.add(new JLabel("Fare: $" + booking.getOfferedFare()));

        // Add cancel button if pending
        if ("Pending".equals(booking.getStatus())) {
            JButton cancelButton = new JButton("Cancel Booking");
            cancelButton.addActionListener(e -> cancelBooking(booking));
            panel.add(cancelButton);
        }

        return panel;
    }

    private void cancelBooking(BookingRequest booking) {
        // Implement cancellation logic here
        JOptionPane.showMessageDialog(this,
                "Booking cancellation feature coming soon!",
                "Info",
                JOptionPane.INFORMATION_MESSAGE);
    }
}