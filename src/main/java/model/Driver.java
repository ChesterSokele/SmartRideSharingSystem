package model;

// Driver class


public class Driver extends User {
    private int driverID;

    public Driver(int userID, String name, String phoneNumber, String email, String password, int driverID) {
        super(userID, name, phoneNumber, email, password);

        this.driverID = driverID;
    }

    public int getId() {
        return driverID;
    }

    public void setId(int driverID) {
        this.driverID = driverID;
    }



    public Ride listRide(String pickupPoint, String destination, int availableSeats, double farePerSeat) {
        return new Ride(this.userID, pickupPoint, destination, availableSeats, farePerSeat);
    }
}

/*public Driver(int id, String name, String email, String phone, String licenseNumber, String vehicleInfo) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.licenseNumber = licenseNumber;
    this.vehicleInfo = vehicleInfo;
}
*/