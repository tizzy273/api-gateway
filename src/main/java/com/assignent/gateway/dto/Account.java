package com.assignent.gateway.dto;

import com.assignent.gateway.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private Integer id;

    private Integer customerId;

    private Float balance;

    private List<Transaction> transactions;

    public Account(Integer customerId) {
        this.customerId = customerId;
    }

    public void updateBalance(List<Transaction> transactions) {

        this.balance = transactions.stream()
                .map(
                        transaction -> transaction.getType().equals(TransactionType.DEPOSIT)  ? transaction.getAmount() : -transaction.getAmount()
                )
                .reduce(0f, Float::sum);
    }
}
