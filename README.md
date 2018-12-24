#### Acme Ride - Passenger Service

* Implemented with Spring Boot - version 1.5.17.RELEASE (aligned with RHOAR release). 
* Spring Kafka client 
* Exposes REST interface to trigger the sending of 1 or more `RideRequestedEvent` messages.


**Using the REST API**

Example with curl:

```
$ curl -X POST -H "Content-type: application/json" -d '{"messages": 1, "type": 1}' http://localhost:8080/simulate
```

* _messages_: number of `RideRequestedEvent` messages to send
* _type_: integer between 0 and 3. Determines the simulation scenario.
    * type 0: random scenario
    * type 1: happy path scenario
    * type 2: passenger cancels ride
    * type 3: no driver can be assigned
