package com.acme.ride.passenger.message;

import com.acme.ride.passenger.message.model.Message;
import com.acme.ride.passenger.message.model.RideRequestedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class RideRequestedMessageSender {

    private static final Logger log = LoggerFactory.getLogger(RideRequestedMessageSender.class);

    @Autowired
    private KafkaTemplate<String, Message<?>> kafkaTemplate;

    @Value("${sender.destination.ride-requested}")
    private String destination;

    public void send(Message<RideRequestedEvent> msg) {
        ListenableFuture<SendResult<String, Message<?>>> future = kafkaTemplate.send(destination, msg.getPayload().getRideId(), msg);
        future.addCallback(
                result -> log.debug("Sent 'RideRequestedEvent' message for ride " + msg.getPayload().getRideId()),
                ex -> log.error("Error sending 'RideRequestedEvent' message for ride " + msg.getPayload().getRideId(), ex));
    }

}
