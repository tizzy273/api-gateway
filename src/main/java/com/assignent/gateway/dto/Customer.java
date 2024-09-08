package com.assignent.gateway.dto;

import com.assignent.gateway.enums.TransactionType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Customer {

    private Integer id;

    private String name;

    private String surname;

    private float balance;

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
                    transaction -> transaction.getType().equals(TransactionType.DEPOSIT)  ? transaction.getAmount() : -transaction.getAmount()
                )
                .reduce(0f, Float::sum);
    }
}
