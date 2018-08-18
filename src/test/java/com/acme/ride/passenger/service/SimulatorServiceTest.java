package com.acme.ride.passenger.service;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.acme.ride.passenger.message.model.Message;
import com.acme.ride.passenger.message.model.RideRequestedEvent;
import com.acme.ride.passenger.message.RideRequestedMessageSender;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

public class SimulatorServiceTest {

    private SimulationService simulationService;

    @Mock
    private RideRequestedMessageSender messageSender;

    @Captor
    private ArgumentCaptor<Message<RideRequestedEvent>> messageCaptor;

    @Before
    public void init() {
        initMocks(this);
        simulationService = new SimulationService();
        setField(simulationService, null, messageSender, RideRequestedMessageSender.class);
    }

    @Test
    public void testSingleMessageType0() {
        simulationService.simulate(1,0);
        verify(messageSender).send(messageCaptor.capture());
        Message<RideRequestedEvent> message = messageCaptor.getValue();
        assertThat(message, notNullValue());
        assertThat(message.getTimestamp().compareTo(new Date()), anyOf(equalTo(-1), equalTo(0)));
        assertThat(message.getMessageType(), equalTo("RideRequestedEvent"));
        assertThat(UUID.fromString(message.getTraceId()), notNullValue());
        assertThat(message.getPayload().getRideId(), notNullValue());
        assertThat(UUID.fromString(message.getPayload().getRideId()), notNullValue());
        assertThat(message.getPayload().getPickup(), notNullValue());
        assertThat(message.getPayload().getPickup().isEmpty(), equalTo(false));
        assertThat(message.getPayload().getDestination().equals(message.getPayload().getPickup()), equalTo(false));
        assertThat(message.getPayload().getPrice(), notNullValue());
        assertThat((message.getPayload().getPrice().intValue() > 0), equalTo(true));
        assertThat(message.getPayload().getPassengerId(), notNullValue());
        assertThat(message.getPayload().getPassengerId().isEmpty(), equalTo(false));
    }

    @Test
    public void testSingleMessageType1() {
        simulationService.simulate(1,1);
        verify(messageSender).send(messageCaptor.capture());
        Message<RideRequestedEvent> message = messageCaptor.getValue();
        assertThat(message, notNullValue());
        assertThat(message.getTimestamp().compareTo(new Date()), anyOf(equalTo(-1), equalTo(0)));
        assertThat(message.getMessageType(), equalTo("RideRequestedEvent"));
        assertThat(UUID.fromString(message.getTraceId()), notNullValue());
        assertThat(message.getPayload().getRideId(), notNullValue());
        assertThat(UUID.fromString(message.getPayload().getRideId()), notNullValue());
        assertThat(((UUID.fromString(message.getPayload().getRideId()).getLeastSignificantBits() & 0x0000000F) < 14 ), equalTo(true));
        assertThat(message.getPayload().getPickup(), notNullValue());
        assertThat(message.getPayload().getPickup().isEmpty(), equalTo(false));
        assertThat(message.getPayload().getDestination().equals(message.getPayload().getPickup()), equalTo(false));
        assertThat(message.getPayload().getPrice(), notNullValue());
        assertThat((message.getPayload().getPrice().intValue() > 0), equalTo(true));
        assertThat(message.getPayload().getPassengerId(), notNullValue());
        assertThat(message.getPayload().getPassengerId().isEmpty(), equalTo(false));
    }

    @Test
    public void testSingleMessageType2() {
        simulationService.simulate(1,2);
        verify(messageSender).send(messageCaptor.capture());
        Message<RideRequestedEvent> message = messageCaptor.getValue();
        assertThat(message, notNullValue());
        assertThat(message.getTimestamp().compareTo(new Date()), anyOf(equalTo(-1), equalTo(0)));
        assertThat(message.getMessageType(), equalTo("RideRequestedEvent"));
        assertThat(UUID.fromString(message.getTraceId()), notNullValue());
        assertThat(message.getPayload().getRideId(), notNullValue());
        assertThat(UUID.fromString(message.getPayload().getRideId()), notNullValue());
        assertThat(((UUID.fromString(message.getPayload().getRideId()).getLeastSignificantBits() & 0x0000000F)), equalTo(14L));
        assertThat(message.getPayload().getPickup(), notNullValue());
        assertThat(message.getPayload().getPickup().isEmpty(), equalTo(false));
        assertThat(message.getPayload().getDestination().equals(message.getPayload().getPickup()), equalTo(false));
        assertThat(message.getPayload().getPrice(), notNullValue());
        assertThat((message.getPayload().getPrice().intValue() > 0), equalTo(true));
        assertThat(message.getPayload().getPassengerId(), notNullValue());
        assertThat(message.getPayload().getPassengerId().isEmpty(), equalTo(false));
    }

    @Test
    public void testSingleMessageType3() {
        simulationService.simulate(1,3);
        verify(messageSender).send(messageCaptor.capture());
        Message<RideRequestedEvent> message = messageCaptor.getValue();
        assertThat(message, notNullValue());
        assertThat(message.getTimestamp().compareTo(new Date()), anyOf(equalTo(-1), equalTo(0)));
        assertThat(message.getMessageType(), equalTo("RideRequestedEvent"));
        assertThat(UUID.fromString(message.getTraceId()), notNullValue());
        assertThat(message.getPayload().getRideId(), notNullValue());
        assertThat(UUID.fromString(message.getPayload().getRideId()), notNullValue());
        assertThat(((UUID.fromString(message.getPayload().getRideId()).getLeastSignificantBits() & 0x0000000F)), equalTo(15L));
        assertThat(message.getPayload().getPickup(), notNullValue());
        assertThat(message.getPayload().getPickup().isEmpty(), equalTo(false));
        assertThat(message.getPayload().getDestination().equals(message.getPayload().getPickup()), equalTo(false));
        assertThat(message.getPayload().getPrice(), notNullValue());
        assertThat((message.getPayload().getPrice().intValue() > 0), equalTo(true));
        assertThat(message.getPayload().getPassengerId(), notNullValue());
        assertThat(message.getPayload().getPassengerId().isEmpty(), equalTo(false));
    }

    @Test
    public void testMultipleMessages() {
        simulationService.simulate(2,0);
        verify(messageSender, times(2)).send(messageCaptor.capture());
        List<Message<RideRequestedEvent>> messages =  messageCaptor.getAllValues();
        assertThat(messages.size(), equalTo(2));
        String requestId1 = messages.get(0).getPayload().getRideId();
        String requestId2 = messages.get(1).getPayload().getRideId();
        assertThat(requestId1.equals(requestId2), equalTo(false));

    }
}
