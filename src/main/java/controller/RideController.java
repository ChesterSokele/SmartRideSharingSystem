package controller;

import dao.RideDAO;
import dao.BookingRequestDAO; // For fetching requests for a ride if needed
import model.BookingRequest;
import model.Driver;
import model.Ride;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class RideController {

    public RideController() {

    }

    // Method for driver to list a new ride
    public void createRide(Driver driver, String pickupPoint, String destination, int availableSeats, double farePerSeat) {
        Ride ride = new Ride(driver.getUserID(), pickupPoint, destination, availableSeats, farePerSeat);



        boolean success = RideDAO.insertRide(ride);

        if (success) {
            System.out.println("🚗 Ride listed by " + driver.getName() + " to " + destination + " with ID: " + ride.getRideID());
        } else {
            System.out.println("❌ Failed to list ride for " + driver.getName());
        }
    }


    public List<Ride> getAvailableRides() {
        return RideDAO.getAvailableRidesFromDB();


    }

    public void startRide(Ride ride) {
        if (ride == null || ride.getRideID() <= 0) {
            System.err.println("❌ Cannot start a null or invalid ride.");
            return;
        }
        ride.setTimeStarted(LocalDateTime.now());
        ride.setStatus("Started");
        boolean updated = RideDAO.updateRide(ride); // Persist changes to DB
        if (updated) {
            System.out.println("✅ Ride to " + ride.getDestination() + " started at " + ride.getTimeStarted());
        } else {
            System.out.println("❌ Failed to update ride status to Started in DB for ride ID: " + ride.getRideID());
            // revert in-memory changes if DB update fails
            ride.setTimeStarted(null);
            ride.setStatus("Pending");
        }
    }



    public boolean requestToJoinRide(Ride ride, int riderID, int requestedSeats, double offeredFare, String pickupPoint) {
        if (ride == null || ride.getRideID() <= 0) {
            System.err.println("❌ Cannot request to join a null or invalid ride.");
            return false;
        }
        // new BookingRequest object
        BookingRequest request = new BookingRequest(riderID, ride.getRideID(), requestedSeats, offeredFare, pickupPoint, "PendingApproval");
        return MainController.bookingController.requestBooking(ride, request);
    }

    public void approveRequest(Ride ride, BookingRequest request) {
        if (ride == null || request == null || ride.getRideID() <= 0) {
            System.err.println("❌ Invalid ride or request object in approveRequest.");
            return;
        }

        if ("Approved".equalsIgnoreCase(request.getStatus())) {
            System.out.println("⚠️ Request already approved for Rider ID: " + request.getRiderID() + " on Ride ID: " + ride.getRideID());
            return;
        }
        if (!"PendingApproval".equalsIgnoreCase(request.getStatus()) && !"Pending".equalsIgnoreCase(request.getStatus())) {
            System.out.println("⚠️ Request is not in a pending state. Current status: " + request.getStatus());
            return;
        }


        if (ride.getAvailableSeats() >= request.getRequestedSeats()) {
            request.setStatus("Approved");
            ride.setAvailableSeats(ride.getAvailableSeats() - request.getRequestedSeats());

            boolean rideUpdated = RideDAO.updateRide(ride);

            if (rideUpdated) {

                System.out.println("✅ Approved request for Rider ID: " + request.getRiderID() + " on Ride ID: " + ride.getRideID());
            } else {
                System.out.println("❌ Failed to update ride (ID: " + ride.getRideID() + ") in database after approval. Reverting seat change.");
                request.setStatus("PendingApproval");
                ride.setAvailableSeats(ride.getAvailableSeats() + request.getRequestedSeats());
            }
        } else {
            System.out.println("❌ Not enough seats to approve this request for Ride ID: " + ride.getRideID());

        }
    }


    // Driver rejects a request
    public void rejectRequest(Ride ride, BookingRequest request) {
        if (request == null) {
            System.err.println("❌ Cannot reject a null request.");
            return;
        }
        request.setStatus("Rejected");
        System.out.println("❌ Rejected request for Rider ID: " + request.getRiderID() + " on Ride ID: " + (ride != null ? ride.getRideID() : "N/A"));
    }

    
    public List<Ride> getAllRides() {
        return RideDAO.getAllRidesFromDB();
    }


    public List<BookingRequest> getRequestsForRide(Ride ride) {
        if (ride == null || ride.getRideID() <= 0) return new ArrayList<>();

        return new ArrayList<>(ride.getRequests());
    }

    public List<BookingRequest> getRiderBookings(int riderID) {
        return BookingRequestDAO.getRiderBookings(riderID);

    }
}
