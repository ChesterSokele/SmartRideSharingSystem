package model;


// Ride class
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Ride {
    private int rideID;
    private int driverID;
    private String pickupPoint;
    private String destination;
    private int availableSeats;
    private double farePerSeat;
    private LocalDateTime timeLogged;
    private LocalDateTime timeStarted;
    private String status;


    public Ride(int driverID, String pickupPoint, String destination, int availableSeats, double farePerSeat) {
        this.driverID = driverID;
        this.pickupPoint = pickupPoint;
        this.destination = destination;
        this.availableSeats = availableSeats;
        this.farePerSeat = farePerSeat;
        this.timeLogged = LocalDateTime.now();
        this.status = "Pending";
        this.requests = new ArrayList<>();
    }



    public void startRide() {
        this.timeStarted = LocalDateTime.now();
        this.status = "Started";
    }

    public void addRequest(BookingRequest request) {
        requests.add(request);
        this.availableSeats -= request.getRequestedSeats();
    }
    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getDestination() {
        return destination;
    }
    public int getDriverID() {
        return driverID;
    }

    public LocalDateTime getTimeStarted() {
        return timeStarted;
    }

    public void setTimeStarted(LocalDateTime timeStarted) {
        this.timeStarted = timeStarted;
    }

    private List<BookingRequest> requests = new ArrayList<>();

    public List<BookingRequest> getRequests() {
        return requests;
    }







}

