package view;

import dao.BookingRequestDAO;
import model.BookingRequest;
import model.Rider;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MyBookingsFrame extends JFrame {
    private final Rider rider;

    public MyBookingsFrame(Rider rider) {
        this.rider = rider;
        initializeUI();
    }

    private void initializeUI() {
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
        backButton.addActionListener(e -> returnToDashboard());

        mainPanel.add(backButton, BorderLayout.SOUTH);
        add(mainPanel);
        setVisible(true);
    }

    private JPanel createBookingPanel(BookingRequest booking) {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
                "Booking ID: " + booking.getRideID() +
                        " - Status: " + booking.getStatus()
        ));

        panel.add(new JLabel("From: " + booking.getPickupPoint()));
        panel.add(new JLabel("To: " + booking.getDestination()));
        panel.add(new JLabel("Seats: " + booking.getRequestedSeats()));
        panel.add(new JLabel("Fare: $" + booking.getOfferedFare()));

        // Add action buttons based on status
        addActionButtons(panel, booking);

        return panel;
    }

    private void addActionButtons(JPanel panel, BookingRequest booking) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        if ("Pending".equals(booking.getStatus())) {
            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e -> handleCancelBooking(booking));
            buttonPanel.add(cancelButton);
        }

        if ("Approved".equals(booking.getStatus())) {
            JButton viewDetailsButton = new JButton("View Details");
            viewDetailsButton.addActionListener(e -> showBookingDetails(booking));
            buttonPanel.add(viewDetailsButton);
        }

        panel.add(buttonPanel);
    }

    private void handleCancelBooking(BookingRequest booking) {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to cancel this booking?",
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = BookingRequestDAO.cancelBooking(booking.getRideID());
            if (success) {
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully!");
                refreshBookings();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to cancel booking",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showBookingDetails(BookingRequest booking) {
        String message = "Booking Details:\n\n" +
                "From: " + booking.getPickupPoint() + "\n" +
                "To: " + booking.getDestination() + "\n" +
                "Seats: " + booking.getRequestedSeats() + "\n" +
                "Fare: $" + booking.getOfferedFare() + "\n" +
                "Status: " + booking.getStatus();

        JOptionPane.showMessageDialog(this, message, "Booking Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshBookings() {
        dispose();
        new MyBookingsFrame(rider);
    }

    private void returnToDashboard() {
        new RiderDashboardFrame(rider);
        dispose();
    }
}