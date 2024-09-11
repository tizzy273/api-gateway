package com.assignent.gateway.dto;

import com.assignent.gateway.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

/**
 * Data Transfer Object for an Account.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private Integer id;

    private Integer customerId;

    private Float balance;

    private List<Transaction> transactions;

    /**
     * Constructs an Account with a given customer ID.
     *
     * @param customerId the ID of the customer
     */
    public Account(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * Updates the account balance based on a list of transactions.
     *
     * @param transactions the list of transactions to update the balance
     */
    public void updateBalance(List<Transaction> transactions) {
        this.balance = transactions.stream()
                .map(transaction -> transaction.getType().equals(TransactionType.DEPOSIT.name()) ? transaction.getAmount() : -transaction.getAmount())
                .reduce(0f, Float::sum);
    }
}
