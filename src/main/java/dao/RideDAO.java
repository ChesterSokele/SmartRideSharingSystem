package dao;

import model.Ride;
// import util.DatabaseConnection; // Assuming you might use this eventually

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RideDAO {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/rss";
    private static final String USER = "root";
    private static final String PASSWORD = "bigbro";


    public static boolean insertRide(Ride ride) {

        String sql = "INSERT INTO Rides (driverID, pickupPoint, destination, availableSeats, farePerSeat, timeLogged, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        boolean success = false;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, ride.getDriverID());
            stmt.setString(2, ride.getPickupPoint());
            stmt.setString(3, ride.getDestination());
            stmt.setInt(4, ride.getAvailableSeats());
            stmt.setDouble(5, ride.getFarePerSeat());
            stmt.setTimestamp(6, Timestamp.valueOf(ride.getTimeLogged() != null ? ride.getTimeLogged() : LocalDateTime.now()));
            stmt.setString(7, ride.getStatus());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        ride.setRideID(generatedKeys.getInt(1));
                        System.out.println("✅ Ride inserted into database with ID: " + ride.getRideID() + " and timestamp!");
                        success = true;
                    } else {
                        System.out.println("❌ Failed to retrieve generated rideID after insert.");
                    }
                }
            } else {
                System.out.println("❌ Failed to insert ride into database, no rows affected.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ SQL Exception during ride insertion.");
        }
        return success;
    }

    public static boolean updateRide(Ride ride) {
        if (ride.getRideID() <= 0) {
            System.err.println("❌ Cannot update ride with invalid ID: " + ride.getRideID());
            return false;
        }
        String sql = "UPDATE Rides SET driverID = ?, pickupPoint = ?, destination = ?, availableSeats = ?, farePerSeat = ?, timeLogged = ?, timeStarted = ?, status = ? WHERE rideID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ride.getDriverID());
            stmt.setString(2, ride.getPickupPoint());
            stmt.setString(3, ride.getDestination());
            stmt.setInt(4, ride.getAvailableSeats());
            stmt.setDouble(5, ride.getFarePerSeat());
            stmt.setTimestamp(6, ride.getTimeLogged() != null ? Timestamp.valueOf(ride.getTimeLogged()) : null);
            stmt.setTimestamp(7, ride.getTimeStarted() != null ? Timestamp.valueOf(ride.getTimeStarted()) : null);
            stmt.setString(8, ride.getStatus());
            stmt.setInt(9, ride.getRideID());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✅ Ride ID: " + ride.getRideID() + " updated successfully.");
                return true;
            } else {
                System.out.println("⚠️ Ride ID: " + ride.getRideID() + " not found or no changes made during update.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ SQL Exception during ride update for ID: " + ride.getRideID());
            return false;
        }
    }

    public static List<Ride> getAvailableRidesFromDB() {
        List<Ride> availableRides = new ArrayList<>();
        String sql = "SELECT rideID, driverID, pickupPoint, destination, availableSeats, farePerSeat, timeLogged, status " +
                "FROM Rides WHERE availableSeats > 0 AND (status = 'Pending' OR status = 'Scheduled')";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Ride ride = new Ride(
                        rs.getInt("rideID"),
                        rs.getInt("driverID"),
                        rs.getString("pickupPoint"),
                        rs.getString("destination"),
                        rs.getInt("availableSeats"),
                        rs.getDouble("farePerSeat"),
                        rs.getTimestamp("timeLogged") != null ? rs.getTimestamp("timeLogged").toLocalDateTime() : null,
                        rs.getString("status")
                );
                availableRides.add(ride);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ SQL Exception while fetching available rides.");
        }
        System.out.println("ℹ️ Fetched " + availableRides.size() + " available rides from DB.");
        return availableRides;
    }

    public static List<Ride> getAllRidesFromDB() {
        List<Ride> allRides = new ArrayList<>();
        String sql = "SELECT rideID, driverID, pickupPoint, destination, availableSeats, farePerSeat, timeLogged, timeStarted, status FROM Rides";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Ride ride = new Ride(
                        rs.getInt("rideID"),
                        rs.getInt("driverID"),
                        rs.getString("pickupPoint"),
                        rs.getString("destination"),
                        rs.getInt("availableSeats"),
                        rs.getDouble("farePerSeat"),
                        rs.getTimestamp("timeLogged") != null ? rs.getTimestamp("timeLogged").toLocalDateTime() : null,
                        rs.getString("status")
                );
                Timestamp timeStartedTS = rs.getTimestamp("timeStarted");
                if (timeStartedTS != null) {
                    ride.setTimeStarted(timeStartedTS.toLocalDateTime());
                }

                allRides.add(ride);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ SQL Exception while fetching all rides.");
        }
        return allRides;
    }

}
