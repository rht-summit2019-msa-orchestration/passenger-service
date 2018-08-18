package com.acme.ride.passenger.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.acme.ride.passenger.service.SimulationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/")
public class PassengerServiceSimulationResource {

    private static Logger log = LoggerFactory.getLogger("PassengerServiceSimulation");

    @Autowired
    private SimulationService simulationService;

    @POST
    @Path("/simulate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String simulate(Simulation s) {
        simulationService.simulate(s.getMessages(), s.getType());
        return "Sent " + s.getMessages() + " message(s) with type " + s.getType();
    }

    public static class Simulation {

        private int messages;

        private int type;

        public int getMessages() {
            return messages;
        }

        public void setMessages(int messages) {
            this.messages = messages;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
