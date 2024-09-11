package com.assignment.accounts.cucumber.glue;

import com.assignment.accounts.dto.Account;
import com.assignment.accounts.dto.Customer;
import com.assignment.accounts.entity.CustomerEntity;
import com.assignment.accounts.mapper.CustomerMapper;
import com.assignment.accounts.repository.CustomerRepository;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerScenario {


    private static final String CUSTOMER_URL = "http://localhost:8081/accounts/customer/customer-info";

    private Customer customer;

    private Response response;

    @Autowired
    private BaseScenario baseScenario;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;


    @Given("an instance of the DB where there are multiple Customer")
    public void givenAnInstanceOfTheDb() {

       customer = customerMapper.toDto(customerRepository.findById(1).orElse(new CustomerEntity()));
    }


    @When("I call the Get Customer endpoint with id = {int}")
    public void iCallTheGetCustomerEndpointWithId(int customerId) {
        response = given()
                .header("Content-Type", "application/json")
                .get(CUSTOMER_URL + "?customer-id="+ customerId);

        baseScenario.setResponse(response);

    }


    @Then("I'll get the corrispondent Customer")
    public void iLlGetTheCorrispondentCustomer() {
        response.then().statusCode(200);

        Customer customerResponse = response.body()
                .jsonPath()
                .getObject(".", Customer.class);



        assertEquals(customer, customerResponse );
    }
}
