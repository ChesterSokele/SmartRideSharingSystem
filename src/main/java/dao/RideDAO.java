package dao;

import model.Ride;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;

public class RideDAO {

    public static void insertRide(Ride ride) {
        String sql = "INSERT INTO Rides (driverID, pickupPoint, destination, availableSeats, farePerSeat, timeLogged, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/rss", "root", "bigbro");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ride.getDriverID());
            stmt.setString(2, ride.getPickupPoint());
            stmt.setString(3, ride.getDestination());
            stmt.setInt(4, ride.getAvailableSeats());
            stmt.setDouble(5, ride.getFarePerSeat());
            stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));  // set timeLogged to now
            stmt.setString(7, ride.getStatus());

            stmt.executeUpdate();
            System.out.println("✅ Ride inserted into database with timestamp!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Failed to insert ride into database.");
        }
    }
}
