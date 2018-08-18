package com.acme.ride.passenger.service;

import com.acme.ride.passenger.message.DriverAssignedEventMessageListenerTest;
import com.acme.ride.passenger.message.PassengerCanceledMessageSender;
import com.acme.ride.passenger.message.RideRequestedMessageSender;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class MessageSenderTestConfiguration {

    @Primary
    @Bean
    public RideRequestedMessageSender rideRequestedMessageSender() {
        return Mockito.mock(RideRequestedMessageSender.class);
    }

    @Primary
    @Bean
    public PassengerCanceledMessageSender passengerCanceledMessageSender() {
        return Mockito.mock(PassengerCanceledMessageSender.class);
    }

    @Primary
    @Bean
    public DriverAssignedEventMessageListenerTest driverAssignedEventMessageListener() {
        return Mockito.mock(DriverAssignedEventMessageListenerTest.class);
    }
}
