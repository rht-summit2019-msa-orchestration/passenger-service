package com.acme.ride.passenger.service;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.acme.ride.passenger.message.RideRequestedMessageSender;
import com.acme.ride.passenger.message.model.Message;
import com.acme.ride.passenger.message.model.RideRequestedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SimulationService {

    @Autowired
    private RideRequestedMessageSender messageSender;

    @Value("${simulator.pool.size}")
    int threadPoolSize;

    @Value("${simulator.delay}")
    int delay;

    private ScheduledExecutorService scheduler;

    private DataGenerator generator = new DataGenerator();

    public void simulate(int messages, int type) {

        //Sanitize input
        if (messages <= 0) {
            messages = 1;
        }
        if (type < 0 || type > 3) {
            type = 0;
        }

        for (int i = 0; i < messages; i++) {
            scheduler.schedule(scheduleSendMessage(type), delay*i, TimeUnit.MILLISECONDS);
        }
    }

    private Runnable scheduleSendMessage(final int type) {
        Runnable runnable = () -> {
            messageSender.send(buildRideRequestedEventMessage(type));
        };
        return runnable;
    }

    private Message<RideRequestedEvent> buildRideRequestedEventMessage(int type) {

        return new Message.Builder<>("RideRequestedEvent", "PassengerServiceSimulator", buildRideRequestedEvent(type))
                .id(UUID.randomUUID().toString())
                .timestamp(new Date())
                .traceId(UUID.randomUUID().toString())
                .build();
    }


    private RideRequestedEvent buildRideRequestedEvent(int type) {

        String[] locations = getLocations();
        return new RideRequestedEvent.Builder(generateRequestId(type).toString())
                .pickup(locations[0])
                .destination(locations[1])
                .passengerId("passenger" + generator.numeric(100,200).intValue())
                .price(generator.decimal(20, 40, 2))
                .build();
    }

    /**
     * type 0: random UUID
     * type 1: random UUID, least significant 4 bits < 14
     * type 2: random UUID, least significant 4 bits = 14
     * type 3: random UUID, least significant 4 bits = 15
     *
     */
    private UUID generateRequestId(int type) {
        UUID uuid = UUID.randomUUID();
        if (type == 1) {
            while ((uuid.getLeastSignificantBits() & 0x0000000F) >= 14) {
                uuid = UUID.randomUUID();
            }
        } else if (type == 2) {
            while ((uuid.getLeastSignificantBits() & 0x0000000F) != 14) {
                uuid = UUID.randomUUID();
            }
        } else if (type == 3) {
            while ((uuid.getLeastSignificantBits() & 0x0000000F) != 15) {
                uuid = UUID.randomUUID();
            }
        }
        return uuid;
    }

    private String[] getLocations() {
        String location1 = generator.location();
        String location2 = location1;
        while (location1.equals(location2)) {
            location2 = generator.location();
        }
        return new String[]{location1, location2};
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
