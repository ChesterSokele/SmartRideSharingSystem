package model;

public class BookingRequest {
    private int riderID;
    private int rideID;
    private int requestedSeats;
    private double offeredFare;
    private String status;
    private String pickupPoint;
    private String destination;



    // Constructor with 4 arguments (default)
    public BookingRequest(int riderID, int rideID, int requestedSeats, double offeredFare) {
        this.riderID = riderID;
        this.rideID = rideID;
        this.requestedSeats = requestedSeats;
        this.offeredFare = offeredFare;
        this.status = "Pending";
    }

    // Constructor with pickupPoint and custom status
    public BookingRequest(int riderID, int rideID, int requestedSeats, double offeredFare, String pickupPoint, String status) {
        this.riderID = riderID;
        this.rideID = rideID;
        this.requestedSeats = requestedSeats;
        this.offeredFare = offeredFare;
        this.pickupPoint = pickupPoint;
        this.status = status;
    }

    // Getters and setters
    public int getRiderID() {
        return riderID;
    }

    public int getRideID() {
        return rideID;
    }

    public int getRequestedSeats() {
        return requestedSeats;
    }

    public double getOfferedFare() {
        return offeredFare;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPickupPoint() {
        return pickupPoint;
    }


    public String getDestination() {
        return destination;
    }

    public void setPickupPoint(String pickupPoint) {
        this.pickupPoint = pickupPoint;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setRideID(int rideID) {
        this.rideID = rideID;
    }
}
