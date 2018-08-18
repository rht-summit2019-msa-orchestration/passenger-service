package com.acme.ride.passenger.message;

import com.acme.ride.passenger.message.model.Message;
import com.acme.ride.passenger.message.model.RideRequestedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class RideRequestedMessageSender {

    private static final Logger log = LoggerFactory.getLogger(RideRequestedMessageSender.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${sender.destination.ride-requested}")
    private String destination;

    public void send(Message<RideRequestedEvent> msg) {
        try {
            String json = new ObjectMapper().writeValueAsString(msg);
            jmsTemplate.convertAndSend(destination, json);
        } catch (JsonProcessingException e) {
            log.error("Error transforming message to json " + msg, e);
            throw new RuntimeException(e);
        }
    }

}
