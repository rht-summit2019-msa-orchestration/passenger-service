package com.acme.ride.passenger.message;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import com.acme.ride.passenger.message.model.Message;
import com.acme.ride.passenger.message.model.PassengerCanceledEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;

public class DriverAssignedEventMessageListenerTest {

    private DriverAssignedEventMessageListener listener;

    @Mock
    private PassengerCanceledMessageSender messageSender;

    @Captor
    private ArgumentCaptor<Message> messageCaptor;

    @Before
    public void setup() {
        initMocks(this);
        listener = new DriverAssignedEventMessageListener();
        setField(listener, "threadPoolSize", 1, null);
        setField(listener, "minDelay", 1, null);
        setField(listener, "maxDelay", 1, null);
        setField(listener, null, messageSender, PassengerCanceledMessageSender.class);
        listener.init();
    }

    @Test
    public void testProcessMessage() throws Exception {

        String json = "{\"messageType\":\"DriverAssignedEvent\"," +
                "\"id\":\"messageId\"," +
                "\"traceId\":\"trace\"," +
                "\"sender\":\"messageSender\"," +
                "\"timestamp\":1521148332397," +
                "\"payload\":{\"rideId\":\"2333b91b-08a6-4056-9707-df65c8abfa50\"," +
                "\"driverId\": \"driver\"}}";

        listener.processMessage(json, "2333b91b-08a6-4056-9707-df65c8abfa50", 1);

        Thread.sleep(1500);
        verify(messageSender, Mockito.never()).send(Mockito.any());

    }

    @Test
    public void testProcessMessageWhenPassengerCancels() throws Exception {

        String rideId = "2333b91b-08a6-4056-9707-df65c8abfa5e";

        String json = "{\"messageType\":\"DriverAssignedEvent\"," +
                "\"id\":\"messageId\"," +
                "\"traceId\":\"trace\"," +
                "\"sender\":\"messageSender\"," +
                "\"timestamp\":1521148332397," +
                "\"payload\":{\"rideId\": \"" + rideId + "\"," +
                "\"driverId\": \"driver\"}}";

        listener.processMessage(json, rideId, 1);

        Thread.sleep(1500);
        verify(messageSender).send(messageCaptor.capture());
        Message<PassengerCanceledEvent> message = messageCaptor.getValue();
        assertThat(message, notNullValue());
        assertThat(message.getPayload().getRideId(), equalTo(rideId));
        assertThat(message.getTraceId(), equalTo("trace"));
        assertThat(message.getMessageType(), equalTo("PassengerCanceledEvent"));
    }
}
