package controller;

import model.BookingRequest;
import model.Ride;

public class BookingController {

    public boolean requestBooking(Ride ride, BookingRequest request) {
        if (ride.getAvailableSeats() >= request.getRequestedSeats()) {
            // maybe notify driver to approve the request first
            request.setStatus("PendingApproval");
            ride.addRequest(request);
            return true;
        }
        return false;
    }


}


