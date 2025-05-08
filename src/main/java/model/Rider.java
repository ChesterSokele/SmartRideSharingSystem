

// Rider.java
package model;

public class Rider extends User {
    public Rider(int userID, String name, String email, String password, String phoneNumber) {
        super(userID, name, email, password, phoneNumber, "Rider");
    }

    public BookingRequest requestRide(int rideID, int requestedSeats, double offeredFare, String pickupPoint) {
        return new BookingRequest(this.userID, rideID, requestedSeats, offeredFare, pickupPoint, "PendingApproval");
    }
}