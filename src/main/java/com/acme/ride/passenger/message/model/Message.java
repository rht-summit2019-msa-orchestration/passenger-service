package com.acme.ride.passenger.message.model;

import java.util.Date;
import java.util.UUID;

public class Message<T> {

    private String messageType;
    private String id;
    private String traceId;
    private String sender;
    private Date timestamp;

    private T payload;

    public String getMessageType() {
        return messageType;
    }

    public String getId() {
        return id;
    }

    public String getTraceId() {
        return traceId;
    }

    public String getSender() {
        return sender;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public T getPayload() {
        return payload;
    }

    public static class Builder<T> {

        private final Message<T> msg;

        public Builder(String messageType, String sender, T payload) {

            this.msg = new Message<>();
            msg.messageType = messageType;
            msg.sender = sender;
            msg.payload = payload;
            msg.id = UUID.randomUUID().toString();
            msg.timestamp = new Date();
        }

        public Builder<T> id(String id) {
            msg.id = id;
            return this;
        }

        public Builder<T> traceId(String traceId) {
            msg.traceId = traceId;
            return this;
        }

        public Builder<T> timestamp(Date timestamp) {
            msg.timestamp = timestamp;
            return this;
        }

        public Message<T> build() {
            return msg;
        }
    }

}
