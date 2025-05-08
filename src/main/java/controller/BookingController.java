package controller;

import model.BookingRequest;
import model.Ride;
import dao.BookingRequestDAO;

public class BookingController {

    public boolean requestBooking(Ride ride, BookingRequest request) {

        if (ride == null || ride.getRideID() <= 0) {
            System.err.println("❌ Invalid ride object or ride ID in requestBooking.");
            return false;
        }
        // Ensure the request has the rideID set from the selected ride
        request.setRideID(ride.getRideID());


        if (ride.getAvailableSeats() >= request.getRequestedSeats()) {
            request.setStatus("PendingApproval"); // Status before saving to DB


            // Save the booking request to the database
            boolean savedToDB = BookingRequestDAO.insertBookingRequest(request);

            if (savedToDB) {
                ride.addRequest(request);
                System.out.println("✅ Booking request processed and saved to DB for ride ID: " + ride.getRideID());
                return true;
            } else {
                System.err.println("❌ Failed to save booking request to database for ride ID: " + ride.getRideID());
                return false;
            }
        }
        System.out.println("ℹ️ Not enough seats available for ride ID: " + ride.getRideID());
        return false;
    }
}
