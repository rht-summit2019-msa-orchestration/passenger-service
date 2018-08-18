package com.acme.ride.passenger.message.model;

public class PassengerCanceledEvent {

    private String rideId;

    private String reason;

    public String getRideId() {
        return rideId;
    }

    public String getReason() {
        return reason;
    }

    public static class Builder {

        private PassengerCanceledEvent event;

        public Builder(String rideId) {
            event = new PassengerCanceledEvent();
            event.rideId = rideId;
        }

        public Builder reason(String reason) {
            event.reason = reason;
            return this;
        }

        public PassengerCanceledEvent build() {
            return event;
        }
    }
}
