package controller;

import dao.RideDAO;
import model.BookingRequest;
import model.Driver;
import model.Ride;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RideController {

    private final List<Ride> rideList = new ArrayList<>();

    // Method for driver to list a new ride
    public void createRide(Driver driver, String pickupPoint, String destination, int availableSeats, double farePerSeat) {
        Ride ride = new Ride(driver.getUserID(), pickupPoint, destination, availableSeats, farePerSeat);
        rideList.add(ride);

        // 💾 Save to database
        RideDAO.insertRide(ride);

        System.out.println("🚗 Ride listed by " + driver.getName() + " to " + destination);
    }

    // Get all rides that haven't started and still have seats
    public List<Ride> getAvailableRides() {
        List<Ride> available = new ArrayList<>();
        for (Ride ride : rideList) {
            if (ride.getAvailableSeats() > 0 && ride.getTimeStarted() == null) {
                available.add(ride);
            }
        }
        return available;
    }

    // Start a specific ride
    public void startRide(Ride ride) {
        ride.setTimeStarted(LocalDateTime.now());
        ride.startRide(); // sets status too
        System.out.println("✅ Ride to " + ride.getDestination() + " started at " + ride.getTimeStarted());
    }


    // In requestToJoinRide method:
    public boolean requestToJoinRide(Ride ride, int riderID, int requestedSeats, double offeredFare, String pickupPoint) {
        BookingRequest request = new BookingRequest(riderID, ride.getRideID(), requestedSeats, offeredFare, pickupPoint, "PendingApproval");
        return MainController.bookingController.requestBooking(ride, request);
    }

    // Driver approves a request
    public void approveRequest(Ride ride, BookingRequest request) {
        if (request.getStatus().equalsIgnoreCase("Approved")) {
            System.out.println("⚠️ Request already approved.");
            return;
        }

        if (ride.getAvailableSeats() >= request.getRequestedSeats()) {
            request.setStatus("Approved");
            ride.setAvailableSeats(ride.getAvailableSeats() - request.getRequestedSeats());
            System.out.println("✅ Approved request for Rider ID: " + request.getRiderID());
        } else {
            System.out.println("❌ Not enough seats to approve this request.");
        }
    }

    // Driver rejects a request
    public void rejectRequest(Ride ride, BookingRequest request) {
        request.setStatus("Rejected");
        System.out.println("❌ Rejected request for Rider ID: " + request.getRiderID());
    }

    // For debugging or admin view
    public List<Ride> getAllRides() {
        return rideList;
    }

    // Get all requests for a specific ride
    public List<BookingRequest> getRequestsForRide(Ride ride) {
        return ride.getRequests();
    }

    public List<BookingRequest> getRiderBookings(int riderID) {
        List<BookingRequest> riderBookings = new ArrayList<>();
        for (Ride ride : rideList) {
            for (BookingRequest request : ride.getRequests()) {
                if (request.getRiderID() == riderID) {
                    riderBookings.add(request);
                }
            }
        }
        return riderBookings;
    }
}
