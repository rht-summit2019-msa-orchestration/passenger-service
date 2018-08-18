package com.acme.ride.passenger.message.model;

import java.math.BigDecimal;

public class RideRequestedEvent {

    private String rideId;

    private String pickup;

    private String destination;

    private BigDecimal price;

    private String passengerId;

    public String getRideId() {
        return rideId;
    }

    public String getPickup() {
        return pickup;
    }

    public String getDestination() {
        return destination;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public static class Builder {

        private RideRequestedEvent event;

        public Builder(String rideId) {
            event = new RideRequestedEvent();
            event.rideId = rideId;
        }

        public Builder pickup(String pickup) {
            event.pickup = pickup;
            return this;
        }

        public Builder destination(String destination) {
            event.destination = destination;
            return this;
        }

        public Builder price(BigDecimal price) {
            event.price = price;
            return this;
        }

        public Builder passengerId(String passengerId) {
            event.passengerId = passengerId;
            return this;
        }

        public RideRequestedEvent build() {
            return event;
        }
    }
}
