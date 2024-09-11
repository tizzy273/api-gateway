package com.assignment.gateway.restclient;

import com.assignment.gateway.dto.Transaction;
import com.assignment.gateway.exception.BadRequestException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * Service for interacting with the Transactions microservice via REST.
 */
@Service
public class TransactionsClient {

    @Autowired
    private WebClient webClient;

    @Value("${transactions.base-url}")
    private String transactionsBaseUrl;

    @Value("${transactions.create-transaction}")
    private String createTransaction;

    @Value("${transactions.transaction-history}")
    private String transactionHistory;

    /**
     * Initializes the WebClient with the base URL for the transactions service.
     */
    @PostConstruct
    public void initBaseUrl() {
        webClient = WebClient.builder().baseUrl(transactionsBaseUrl).build();
    }

    /**
     * Adds a new transaction.
     *
     * @param transaction the transaction details to add.
     * @return the updated list of transactions for the account.
     * @throws BadRequestException if the request fails with a 4xx error.
     */
    public List<Transaction> addTransaction(Transaction transaction) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path(createTransaction).build())
                .bodyValue(transaction)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .map(BadRequestException::new))
                .bodyToMono(new ParameterizedTypeReference<List<Transaction>>() {})
                .block();
    }

    /**
     * Retrieves the transaction history for a specific account.
     *
     * @param accountId the ID of the account.
     * @return the list of transactions for the account.
     * @throws BadRequestException if the request fails with a 4xx error.
     */
    public List<Transaction> getTransactionsHistory(Integer accountId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(transactionHistory)
                        .queryParam("account-id", accountId)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .map(BadRequestException::new))
                .bodyToMono(new ParameterizedTypeReference<List<Transaction>>() {})
                .block();
    }
}
