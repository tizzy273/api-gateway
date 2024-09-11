package com.assignent.gateway.dto;

import com.assignent.gateway.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private Integer id;

    private String name;

    private String surname;

    private float balance;

    List<Transaction> transactions;

    List<Account> accounts;

    public Customer(Integer id, String name, String surname, Float balance) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.balance = balance;
    }

    public void updateBalance(List<Transaction> transactions) {
        balance += transactions.stream()
                .map(
                    transaction -> transaction.getType().equals(TransactionType.DEPOSIT.name())  ? transaction.getAmount() : -transaction.getAmount()
                )
                .reduce(0f, Float::sum);
    }

    public void updateTransactions(List<Transaction> transactions) {
        this.transactions.addAll(transactions);
        this.transactions.sort(Comparator.comparing(Transaction::getTimeStamp).reversed());
    }
}
