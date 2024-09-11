package com.assignent.gateway.dto;

import com.assignent.gateway.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

/**
 * Data Transfer Object representing a customer.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private Integer id;
    private String name;
    private String surname;
    private float balance;
    private List<Transaction> transactions;
    private List<Account> accounts;

    /**
     * Constructs a Customer with the given id, name, surname, and balance.
     *
     * @param id the customer's ID
     * @param name the customer's name
     * @param surname the customer's surname
     * @param balance the customer's balance
     */
    public Customer(Integer id, String name, String surname, Float balance) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.balance = balance;
    }

    /**
     * Updates the customer's balance based on the provided list of transactions.
     *
     * @param transactions the list of transactions to update the balance
     */
    public void updateBalance(List<Transaction> transactions) {
        balance += transactions.stream()
                .map(transaction -> transaction.getType().equals(TransactionType.DEPOSIT.name()) ? transaction.getAmount() : -transaction.getAmount())
                .reduce(0f, Float::sum);
    }

    /**
     * Updates the customer's transaction history with the provided transactions.
     * The transactions are added and sorted by timestamp in descending order.
     *
     * @param transactions the list of transactions to update the history
     */
    public void updateTransactions(List<Transaction> transactions) {
        this.transactions.addAll(transactions);
        this.transactions.sort(Comparator.comparing(Transaction::getTimeStamp).reversed());
    }
}
