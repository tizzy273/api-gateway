package com.assignment.accounts.cucumber.glue;

import com.assignment.accounts.dto.ErrorDto;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import lombok.Data;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Data
public class BaseScenario {

    private Response response;

    @Then("I'll get HTTP Status Code {int} with message {string}")
    public void iLlGetHTTPStatusCode(int statusCode, String messaggio) {

        response.then().statusCode(statusCode);
        ErrorDto exposureError = response.body()
                .jsonPath()
                .getObject(".", ErrorDto.class);

        assertEquals(messaggio, exposureError.getMessage());
    }
}
