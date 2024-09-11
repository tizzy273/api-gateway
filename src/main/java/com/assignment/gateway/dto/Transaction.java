package com.assignment.gateway.dto;

import com.assignment.gateway.enums.TransactionType;
import com.assignment.gateway.validation.ValueOfEnum;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object representing a financial transaction.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Transaction {

    /**
     * Unique identifier for the transaction.
     */
    private Integer id;

    /**
     * Type of the transaction, validated to be a value from {@link TransactionType}.
     */
    @Valid
    @ValueOfEnum(enumClass = TransactionType.class)
    private String type;

    /**
     * Identifier of the account associated with the transaction.
     */
    private Integer accountId;

    /**
     * Timestamp when the transaction occurred.
     */
    private LocalDateTime timeStamp;

    /**
     * Amount of money involved in the transaction.
     */
    private Float amount;

    /**
     * Constructor for creating a transaction with type, accountId, and amount.
     *
     * @param type the type of the transaction.
     * @param accountId the ID of the account associated with the transaction.
     * @param amount the amount of money involved in the transaction.
     */
    public Transaction(String type, Integer accountId, Float amount) {
        this.type = type;
        this.accountId = accountId;
        this.amount = amount;
    }
}
