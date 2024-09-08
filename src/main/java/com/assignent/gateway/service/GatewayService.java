package com.assignent.gateway.service;

import com.assignent.gateway.dto.Account;
import com.assignent.gateway.dto.CreateAccountRequest;
import com.assignent.gateway.dto.Customer;
import com.assignent.gateway.dto.Transaction;
import com.assignent.gateway.enums.TransactionType;
import com.assignent.gateway.restclient.AccountsClient;
import com.assignent.gateway.restclient.TransactionsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GatewayService {

    @Autowired
    private TransactionsClient transactionsClient;


    @Autowired
    private AccountsClient accountsClient;


    public Account createAccount(CreateAccountRequest createAccountRequest) {

        Account account = accountsClient.createAccount(new Account(createAccountRequest.getCustomerId()));

        if(createAccountRequest.getInitialCredit() != null &&  createAccountRequest.getInitialCredit() > 0){
            account.setTransactions(addTransaction(new Transaction(TransactionType.DEPOSIT, account.getId(),createAccountRequest.getInitialCredit())));
        }

        return account;
    }


    public Customer getCustomerById(Integer customerId) {
        Customer customer =  accountsClient.userInfo(customerId);


        customer.getAccounts().forEach(
                account -> {
                    account.setTransactions(
                            transactionsClient.getTransactionsHistory(account.getId()));

                            customer.updateBalance(account.getTransactions());

                }

        );



        return customer;
    }



    public List<Transaction> addTransaction(Transaction transaction){
        return transactionsClient.addTransaction(transaction);
    }
}
