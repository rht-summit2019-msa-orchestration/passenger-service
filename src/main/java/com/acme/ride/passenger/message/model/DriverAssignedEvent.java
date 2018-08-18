package com.acme.ride.passenger.message.model;

public class DriverAssignedEvent {

    String rideId;

    String driverId;

    public String getRideId() {
        return rideId;
    }

    public String getDriverId() {
        return driverId;
    }
}
