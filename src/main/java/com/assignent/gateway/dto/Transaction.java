package com.assignent.gateway.dto;

import com.assignent.gateway.enums.TransactionType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Transaction {

    private Integer id;

    private TransactionType type;

    private Integer accountId;

    private LocalDateTime timeStamp;

    private Float amount;

    public Transaction(TransactionType type,  Integer accountId, Float amount) {
        this.type = type;
        this.accountId = accountId;
        this.amount = amount;
    }
}
