// Main.java
package org.example;

import controller.BookingController;
import controller.MainController;
import controller.RideController;
import controller.UserController;
import view.LoginFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Initializing controllers first
            MainController.rideController = new RideController();
            MainController.userController = new UserController();
            MainController.bookingController = new BookingController();

            new LoginFrame().setVisible(true);
        });
    }
}
