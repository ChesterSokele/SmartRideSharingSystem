// Main.java
package org.example;

import view.LoginFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Initialize controllers first
            MainController.rideController = new RideController();
            MainController.userController = new UserController();
            MainController.bookingController = new BookingController();

            new LoginFrame().setVisible(true);
        });
    }
}
