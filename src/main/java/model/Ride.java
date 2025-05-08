package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Ride {
    private int rideID; // Make sure this is populated, typically from DB after insertion or when fetched
    private int driverID;
    private String pickupPoint;
    private String destination;
    private int availableSeats;
    private double farePerSeat;
    private LocalDateTime timeLogged;
    private LocalDateTime timeStarted;
    private String status;
    private List<BookingRequest> requests; // Renamed from 'requests' to avoid conflict if any, though it was fine.

    // Constructor
    public Ride(int driverID, String pickupPoint, String destination, int availableSeats, double farePerSeat) {
        // this.rideID = 0; // Or some other default if not immediately known.
        // It's better if rideID is set upon retrieval from DB or after insertion.
        this.driverID = driverID;
        this.pickupPoint = pickupPoint;
        this.destination = destination;
        this.availableSeats = availableSeats;
        this.farePerSeat = farePerSeat;
        this.timeLogged = LocalDateTime.now();
        this.status = "Pending"; // Default status
        this.requests = new ArrayList<>();
    }

    // Overloaded constructor if you fetch Ride objects that already have an ID
    public Ride(int rideID, int driverID, String pickupPoint, String destination, int availableSeats, double farePerSeat, LocalDateTime timeLogged, String status) {
        this.rideID = rideID;
        this.driverID = driverID;
        this.pickupPoint = pickupPoint;
        this.destination = destination;
        this.availableSeats = availableSeats;
        this.farePerSeat = farePerSeat;
        this.timeLogged = timeLogged;
        this.status = status;
        this.requests = new ArrayList<>(); // Initialize, requests can be loaded separately
    }


    public void startRide() {
        this.timeStarted = LocalDateTime.now();
        this.status = "Started";
    }

    // --- MODIFIED METHOD ---
    /**
     * Adds a booking request to this ride's list of requests.
     * Seats are no longer decremented here; they are decremented upon approval.
     * @param request The booking request to add.
     */
    public void addRequest(BookingRequest request) {
        if (this.requests == null) {
            this.requests = new ArrayList<>();
        }
        this.requests.add(request);
        // DO NOT DECREMENT SEATS HERE: this.availableSeats -= request.getRequestedSeats();
        // Seats are decremented when a request is APPROVED by the driver.
    }
    // --- END OF MODIFIED METHOD ---

    // Getters
    public int getRideID() {
        return rideID;
    }
    public int getDriverID() {
        return driverID;
    }
    public String getPickupPoint() {
        return pickupPoint;
    }
    public String getDestination() {
        return destination;
    }
    public int getAvailableSeats() {
        return availableSeats;
    }
    public double getFarePerSeat() {
        return farePerSeat;
    }
    public LocalDateTime getTimeLogged() {
        return timeLogged;
    }
    public LocalDateTime getTimeStarted() {
        return timeStarted;
    }
    public String getStatus() {
        return status;
    }
    public List<BookingRequest> getRequests() {
        if (this.requests == null) {
            this.requests = new ArrayList<>(); // Defensive initialization
        }
        return requests;
    }

    // Setters
    public void setRideID(int rideID) { // Important for when RideDAO fetches or inserts
        this.rideID = rideID;
    }
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
    public void setTimeStarted(LocalDateTime timeStarted) {
        this.timeStarted = timeStarted;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setPickupPoint(String pickupPoint) {
        this.pickupPoint = pickupPoint;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public void setFarePerSeat(double farePerSeat) {
        this.farePerSeat = farePerSeat;
    }
    public void setTimeLogged(LocalDateTime timeLogged) {
        this.timeLogged = timeLogged;
    }
    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }
    public void setRequests(List<BookingRequest> requests) {
        this.requests = requests;
    }
}
