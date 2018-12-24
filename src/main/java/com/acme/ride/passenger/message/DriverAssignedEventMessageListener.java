package com.acme.ride.passenger.message;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.acme.ride.passenger.message.model.DriverAssignedEvent;
import com.acme.ride.passenger.message.model.Message;
import com.acme.ride.passenger.message.model.PassengerCanceledEvent;
import com.acme.ride.passenger.service.DataGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class DriverAssignedEventMessageListener {

    private final static Logger log = LoggerFactory.getLogger(DriverAssignedEventMessageListener.class);

    private ScheduledExecutorService scheduler;

    @Value("${scheduler.pool.size}")
    int threadPoolSize;

    @Value("${scheduler.delay.min}")
    int minDelay;

    @Value("${scheduler.delay.max}")
    int maxDelay;

    @Autowired
    private PassengerCanceledMessageSender messageSender;

    @KafkaListener(topics = "${listener.destination.driver-assigned}")
    public void processMessage(@Payload String messageAsJson, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {

        if (!accept(messageAsJson)) {
            return;
        }

        Message<DriverAssignedEvent> message = null;
        try {
            message = new ObjectMapper().readValue(messageAsJson, new TypeReference<Message<DriverAssignedEvent>>() {
            });
        } catch (Exception e) {
            log.error("Error processing msg " + messageAsJson, e);
            throw new IllegalStateException(e.getMessage(), e);
        }

        log.debug("Consumed 'DriverAssignedEvent' message for ride " + message.getPayload().getRideId() + "from partition " + partition);

        if (passengerCanceled(message.getPayload().getRideId())) {
            int delay = new DataGenerator().numeric(minDelay, maxDelay).intValue();
            scheduler.schedule(scheduleSendMessage(buildPassengerCanceledMessage(message)), delay, TimeUnit.SECONDS);
        }
    }

    private boolean passengerCanceled(String rideId) {
        try {
            UUID uuid = UUID.fromString(rideId);
            long leastSignificantBits = uuid.getLeastSignificantBits() & 0x0000000F;
            if (leastSignificantBits == 14) {
                log.debug("Passenger is canceling ride " + rideId);
                return true;
            }
        } catch (IllegalArgumentException e) {
            // rideId is not an UUID
            return false;
        }
        return false;
    }

    private Message<PassengerCanceledEvent> buildPassengerCanceledMessage(final Message<DriverAssignedEvent> msgIn) {

        String rideId = msgIn.getPayload().getRideId();
        String traceId = msgIn.getTraceId();

        return new Message.Builder<PassengerCanceledEvent>("PassengerCanceledEvent", "PassengerServiceSimulator",
                    new PassengerCanceledEvent.Builder(rideId).reason("Driver did not show up").build())
                    .traceId(traceId).build();
    }

    private boolean accept(String messageAsJson) {
        try {
            String messageType = JsonPath.read(messageAsJson, "$.messageType");
            if (!"DriverAssignedEvent".equalsIgnoreCase(messageType) ) {
                log.debug("Message with type '" + messageType + "' is ignored");
                return false;
            }
            return true;
        } catch (Exception e) {
            log.warn("Unexpected message without 'messageType' field.");
            return false;
        }
    }

    private Runnable scheduleSendMessage(final Message<PassengerCanceledEvent> message) {
        return () -> {
            log.debug("About to send 'PassengerCanceled' message for ride " + message.getPayload().getRideId());
            messageSender.send(message);
        };
    }

    @PostConstruct
    public void init() {
        scheduler = Executors.newScheduledThreadPool(threadPoolSize);
    }

    @PreDestroy
    public void destroy() {
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
    }

}
