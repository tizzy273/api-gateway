package com.assignent.gateway.dto;

import com.assignent.gateway.enums.TransactionType;
import com.assignent.gateway.validation.ValueOfEnum;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Transaction {

    private Integer id;

    @Valid
    @ValueOfEnum(enumClass = TransactionType.class)
    private String type;

    private Integer accountId;

    private LocalDateTime timeStamp;

    private Float amount;

    public Transaction(String type,  Integer accountId, Float amount) {
        this.type = type;
        this.accountId = accountId;
        this.amount = amount;
    }
}
