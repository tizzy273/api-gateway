package com.assignment.gateway.restclient;

import com.assignment.gateway.dto.Account;
import com.assignment.gateway.dto.Customer;
import com.assignment.gateway.exception.BadRequestException;
import com.assignment.gateway.exception.ResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Service for interacting with the Accounts microservice via REST.
 */
@Service
public class AccountsClient {

    @Autowired
    private WebClient webClient;

    @Value("${accounts.base-url}")
    private String accountsBaseUrl;

    @Value("${accounts.create-account}")
    private String createAccount;

    @Value("${accounts.customer-info}")
    private String customerInfo;

    @Value("${accounts.account}")
    private String accountById;

    /**
     * Initializes the WebClient with the base URL for the accounts service.
     */
    @PostConstruct
    public void initBaseUrl() {
        webClient = WebClient.builder().baseUrl(accountsBaseUrl).build();
    }

    /**
     * Creates a new account.
     *
     * @param account the account details to create.
     * @return the created account.
     * @throws BadRequestException if the request fails with a 4xx error.
     */
    public Account createAccount(Account account) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path(createAccount).build())
                .bodyValue(account)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .map(BadRequestException::new))
                .bodyToMono(Account.class)
                .block();
    }

    /**
     * Retrieves customer information by ID.
     *
     * @param customerId the ID of the customer.
     * @return the customer details.
     * @throws ResourceNotFoundException if the request fails with a 4xx error.
     */
    public Customer customerInfo(Integer customerId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(customerInfo)
                        .queryParam("customer-id", customerId)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .map(ResourceNotFoundException::new))
                .bodyToMono(Customer.class)
                .block();
    }

    /**
     * Retrieves an account by its ID.
     *
     * @param accountId the ID of the account.
     * @return the account details.
     * @throws ResourceNotFoundException if the request fails with a 4xx error.
     */
    public Account getAccountById(Integer accountId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(accountById)
                        .queryParam("id", accountId)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .map(ResourceNotFoundException::new))
                .bodyToMono(Account.class)
                .block();
    }
}
