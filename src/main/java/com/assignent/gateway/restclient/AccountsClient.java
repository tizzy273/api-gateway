package com.assignent.gateway.restclient;


import com.assignent.gateway.dto.Account;
import com.assignent.gateway.dto.Customer;
import com.assignent.gateway.exception.BadRequestException;
import com.assignent.gateway.exception.ResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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



    public Customer customerInfo(Integer customerId) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                        .path(customerInfo)
                        .queryParam("customer-id", customerId)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(Exception.class)
                                .map(e -> {
                                    throw new ResourceNotFoundException(e.getMessage());
                                }))
                .bodyToMono(Customer.class)
                .block();
    }


    public  Account getAccountById(Integer accountId) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                        .path(accountById)
                        .queryParam("id", accountId)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(Exception.class)
                                .map(e -> {
                                    throw new ResourceNotFoundException(e.getMessage());
                                }))
                .bodyToMono(Account.class)
                .block();
    }
}
