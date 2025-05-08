package dao;

import model.BookingRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRequestDAO {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/rss";
    private static final String USER = "root";
    private static final String PASSWORD = "bigbro";


    public static boolean insertBookingRequest(BookingRequest request) {
        String sql = "INSERT INTO BookingRequests (riderID, rideID, requestedSeats, offeredFare, pickupPoint, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, request.getRiderID());
            stmt.setInt(2, request.getRideID());
            stmt.setInt(3, request.getRequestedSeats());
            stmt.setDouble(4, request.getOfferedFare());
            stmt.setString(5, request.getPickupPoint());
            stmt.setString(6, request.getStatus());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✅ BookingRequest inserted into database for rideID: " + request.getRideID());
                return true;
            } else {
                System.out.println("❌ BookingRequest insertion failed, no rows affected for rideID: " + request.getRideID());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("❌ SQL Exception during BookingRequest insertion for rideID: " + request.getRideID());
            e.printStackTrace();
            return false;
        }
    }


    public static List<BookingRequest> getRiderBookings(int riderID) {
        List<BookingRequest> bookings = new ArrayList<>();

        String sql = "SELECT br.*, r.destination, r.pickupPoint as ridePickupPoint " +
                "FROM BookingRequests br " +
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
                // Set destination from the joined Rides table
                booking.setDestination(rs.getString("destination"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public static boolean cancelBooking(int requestID) {

        String sql = "UPDATE BookingRequests SET status = 'Cancelled' WHERE requestID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, requestID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}