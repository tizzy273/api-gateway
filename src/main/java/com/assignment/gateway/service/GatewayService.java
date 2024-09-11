package com.assignment.gateway.service;

import com.assignment.gateway.dto.Account;
import com.assignment.gateway.dto.CreateAccountRequest;
import com.assignment.gateway.dto.Customer;
import com.assignment.gateway.dto.Transaction;
import com.assignment.gateway.enums.TransactionType;
import com.assignment.gateway.exception.BadRequestException;
import com.assignment.gateway.restclient.AccountsClient;
import com.assignment.gateway.restclient.TransactionsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service that handles operations involving accounts, transactions, and customers.
 */
@Service
public class GatewayService {

    @Autowired
    private TransactionsClient transactionsClient;

    @Autowired
    private AccountsClient accountsClient;

    /**
     * Creates a new account and optionally adds an initial credit.
     *
     * @param createAccountRequest the request containing customer ID and optional initial credit.
     * @return the created account with transactions if initial credit was provided.
     * @throws BadRequestException if the initial credit is negative.
     */
    public Account createAccount(CreateAccountRequest createAccountRequest) {
        Float initialCredit = createAccountRequest.getInitialCredit();
        if (initialCredit != null && initialCredit < 0)
            throw new BadRequestException("The initial credit cannot be a negative value");

        Account account = accountsClient.createAccount(new Account(createAccountRequest.getCustomerId()));

        if (initialCredit != null && initialCredit > 0) {
            Transaction depositTransaction = new Transaction(TransactionType.DEPOSIT.name(), account.getId(), initialCredit);
            account.setTransactions(addTransaction(depositTransaction));
        }

        return account;
    }

    /**
     * Retrieves customer information by ID, including their accounts and transactions.
     *
     * @param customerId the ID of the customer to retrieve.
     * @return the customer with updated account balances and transactions.
     */
    public Customer getCustomerById(Integer customerId) {
        Customer customer = accountsClient.customerInfo(customerId);

        customer.setTransactions(new ArrayList<>());

        if (customer.getAccounts() != null) {
            customer.getAccounts().forEach(account -> {
                account.setTransactions(transactionsClient.getTransactionsHistory(account.getId()));

                customer.updateBalance(account.getTransactions());
                customer.updateTransactions(account.getTransactions());

                account.updateBalance(account.getTransactions());
            });
        }

        return customer;
    }

    /**
     * Adds a new transaction to the system.
     *
     * @param transaction the transaction details to add.
     * @return the updated list of transactions for the account.
     */
    public List<Transaction> addTransaction(Transaction transaction) {
        accountsClient.getAccountById(transaction.getAccountId());

        return transactionsClient.addTransaction(transaction);
    }
}
