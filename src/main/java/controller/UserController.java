// UserController.java
package controller;

import model.Driver;
import model.Rider;
import model.User;

import java.sql.*;

public class UserController {
    private Connection connection;

    public UserController() {
        try {

            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/rss", "root", "bigbro");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User loginUser(String email, String password) {
        String query = "SELECT * FROM Users WHERE email = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("userID");
                String name = rs.getString("name");
                String userEmail = rs.getString("email");
                String userPassword = rs.getString("password");
                String phone = rs.getString("phoneNumber");
                String role = rs.getString("role");

                return role.equals("Driver")
                        ? new Driver(id, name, userEmail, userPassword, phone)
                        : new Rider(id, name, userEmail, userPassword, phone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean registerUser(String name, String email, String password, String phoneNumber, String role) {
        String query = "INSERT INTO Users (name, email, password, phoneNumber, role) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, phoneNumber);
            stmt.setString(5, role);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}