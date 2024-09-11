package com.assignent.gateway.service;

import com.assignent.gateway.dto.Account;
import com.assignent.gateway.dto.CreateAccountRequest;
import com.assignent.gateway.dto.Customer;
import com.assignent.gateway.dto.Transaction;
import com.assignent.gateway.enums.TransactionType;
import com.assignent.gateway.exception.BadRequestException;
import com.assignent.gateway.restclient.AccountsClient;
import com.assignent.gateway.restclient.TransactionsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GatewayService {

    @Autowired
    private TransactionsClient transactionsClient;


    @Autowired
    private AccountsClient accountsClient;


    public Account createAccount(CreateAccountRequest createAccountRequest) {

        Float initialCredit = createAccountRequest.getInitialCredit();
        if(initialCredit != null && initialCredit < 0)
            throw new BadRequestException("The initial credit cannot be a negative value");

        Account account = accountsClient.createAccount(new Account(createAccountRequest.getCustomerId()));

        if( initialCredit != null &&  initialCredit > 0){
            Transaction depositTransaction = new Transaction(TransactionType.DEPOSIT.name(), account.getId(), initialCredit);
            account.setTransactions(addTransaction(depositTransaction));
        }

        return account;
    }


    public Customer getCustomerById(Integer customerId) {
        Customer customer = accountsClient.customerInfo(customerId);

        customer.setTransactions(new ArrayList<>());


        customer.getAccounts().forEach(
                account -> {
                    account.setTransactions(
                            transactionsClient. getTransactionsHistory(account.getId()));

                    customer.updateBalance(account.getTransactions());
                    customer.updateTransactions(account.getTransactions());

                    account.updateBalance(account.getTransactions());

                }

        );


        return customer;
    }


    public List<Transaction> addTransaction(Transaction transaction) {
        accountsClient.getAccountById(transaction.getAccountId());

        return transactionsClient.addTransaction(transaction);
    }

}
