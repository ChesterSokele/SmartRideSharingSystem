// Driver.java
package model;

public class Driver extends User {
    public Driver(int userID, String name, String email, String password, String phoneNumber) {
        super(userID, name, email, password, phoneNumber, "Driver");
    }

    public Ride listRide(String pickupPoint, String destination, int availableSeats, double farePerSeat) {
        return new Ride(this.userID, pickupPoint, destination, availableSeats, farePerSeat);
    }
}