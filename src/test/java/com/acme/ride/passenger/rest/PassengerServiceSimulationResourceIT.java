package com.acme.ride.passenger.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PassengerServiceSimulationResourceIT {

    @Value("${local.server.port}")
    private int port;

    @Before
    public void beforeTest() {
        RestAssured.baseURI = String.format("http://localhost:%d", port);
    }

    @Test
    public void testSimulate() throws Exception {
        Response response = given().header(new Header("Content-type", "application/json"))
                .header(new Header("Accept", "application/json"))
                .request().body("{\"messages\": 1, \"type\": 0}").post("/simulate");
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.body().asString(), equalTo("Sent 1 message(s) with type 0"));
    }


}
