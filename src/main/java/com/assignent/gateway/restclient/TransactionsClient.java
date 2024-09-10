package com.assignent.gateway.restclient;


import com.assignent.gateway.dto.Transaction;
import com.assignent.gateway.exception.BadRequestException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

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


    @PostConstruct
    public void initBaseUrl(){
        webClient = WebClient.builder().baseUrl(transactionsBaseUrl).build();
    }


    public List<Transaction> addTransaction(Transaction transaction){
        return webClient.post().uri(uriBuilder -> uriBuilder
                        .path(createTransaction).build())
                .bodyValue(transaction)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(Exception.class)
                                .map(e -> {
                                    throw new BadRequestException(e.getMessage());
                                }))
                .bodyToMono(new ParameterizedTypeReference<List<Transaction>>(){})
                .block();
    }


    public List<Transaction> getTransactionsHistory(Integer accountId){
        return webClient.get().uri(uriBuilder -> uriBuilder
                        .path(transactionHistory)
                        .queryParam("account-id", accountId)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(Exception.class)
                                .map(e -> {
                                    throw new BadRequestException(e.getMessage());
                                }))
                .bodyToMono(new ParameterizedTypeReference<List<Transaction>>(){})
                .block();
    }

}
