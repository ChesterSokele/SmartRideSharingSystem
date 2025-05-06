package dao;

import model.BookingRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRequestDAO {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/rss";
    private static final String USER = "root";
    private static final String PASSWORD = "bigbro";

    public static List<BookingRequest> getRiderBookings(int riderID) {
        List<BookingRequest> bookings = new ArrayList<>();
        String sql = "SELECT br.*, r.pickupPoint, r.destination FROM BookingRequests br " +
                "JOIN Rides r ON br.rideID = r.rideID " +
                "WHERE br.riderID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, riderID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BookingRequest booking = new BookingRequest(
                        rs.getInt("riderID"),
                        rs.getInt("rideID"),
                        rs.getInt("requestedSeats"),
                        rs.getDouble("offeredFare"),
                        rs.getString("pickupPoint"),
                        rs.getString("status")
                );
                // Add ride details to display
                booking.setPickupPoint(rs.getString("pickupPoint"));
                booking.setDestination(rs.getString("destination"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
}