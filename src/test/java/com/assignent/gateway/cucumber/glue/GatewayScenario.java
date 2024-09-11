package com.assignent.gateway.cucumber.glue;

import com.assignment.accounts.dto.Account;
import com.assignment.accounts.entity.AccountEntity;
import com.assignment.accounts.entity.CustomerEntity;
import com.assignment.accounts.mapper.AccountMapper;
import com.assignment.accounts.repository.AccountRepository;
import com.assignment.accounts.repository.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;

public class AccountScenario {

    private static final String BASE_URL = "http://localhost:8081/accounts/account";

    private Response response;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BaseScenario baseScenario;


    private Account account;

    private String entityToJson(Account account) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        return ow.writeValueAsString(account);
    }

    @Before
    public void cleanDb(){
        accountRepository.deleteAll();
    }


    @Given("an Account with Customer ID = {int}")
    public void givenAnAccount(Integer customerId){
        account = new Account(customerId);
    }

    @When("I call the Create Account endpoint")
    public void iCallTheCreateAccountEndpoint() throws JsonProcessingException {
        response = given()
                .header("Content-Type", "application/json")
                .body(entityToJson(account))
                .post(BASE_URL + "/create-account");

                baseScenario.setResponse(response);
    }

    @Then("the account should be created successfully")
    public void theAccountShouldBeCreatedSuccessfully() {

        response.then().statusCode(200);

        Account accountResponse = response.body()
                .jsonPath()
                .getObject(".", Account.class);
        assertEquals(account.getCustomerId(), accountResponse.getCustomerId());
    }



    @Given("an Account with no Customer Id")
    public void anAccountWithNoCustomerId() {
        account = new Account();
    }

    @Given("an instance of the DB where there are multiple Accounts")
    public void anInstanceOfTheDBWhereThereAreMultipleAccounts() {
        CustomerEntity customerEntity = customerRepository.findById(1).orElse(null);


        accountRepository.save(new AccountEntity(customerEntity));
        accountRepository.save(new AccountEntity(customerEntity));
        accountRepository.save(new AccountEntity(customerEntity ));
        account = accountMapper.toDto(accountRepository.save(new AccountEntity(customerEntity)));

    }

    @When("I call the Get Account endpoint")
    public void iCallTheGetAccountEndpoint() {
        response = given()
            .header("Content-Type", "application/json")
            .get(BASE_URL + "?id="+ account.getId());

        baseScenario.setResponse(response);

    }

    @Then("I'll get the corrispondent Account")
    public void iLlGetTheCorrispondentAccount() {
        response.then().statusCode(200);
        Account accountResponse = response.body()
                .jsonPath()
                .getObject(".", Account.class);

        assertEquals(account, accountResponse );
    }

    @When("I call the Get Account endpoint with a wrong ID")
    public void iCallTheGetAccountEndpointWithAWrongID() {
        response = given()
                .header("Content-Type", "application/json")
                .get(BASE_URL + "?id="+ -1);
    }
}
