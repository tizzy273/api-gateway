package com.assignent.gateway.restclient;


import com.assignent.gateway.dto.Account;
import com.assignent.gateway.dto.Customer;
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
public class AccountsClient {


    @Autowired
    private WebClient webClient;

    @Value("${accounts.base-url}")
    private String accountsBaseUrl;

    @Value("${accounts.create-account}")
    private String createAccount;

    @Value("${accounts.user-info}")
    private String userInfo;

        @PostConstruct
        public void initBaseUrl(){
            webClient = WebClient.builder().baseUrl(accountsBaseUrl).build();
        }

    public Account createAccount(Account account) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path(createAccount).build())
                .bodyValue(account)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .map(e -> {
                                    throw new BadRequestException(e);
                                }))
                .bodyToMono(Account.class)
                .block();
    }



    public Customer userInfo(Integer customerId) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                        .path(userInfo)
                        .queryParam("customer-id", customerId)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .map(e -> {
                                    throw new BadRequestException(e);
                                }))
                .bodyToMono(Customer.class)
                .block();
    }
}
