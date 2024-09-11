package com.assignment.gateway.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for creating an account.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {

    @NotNull
    private Integer customerId;

    private Float initialCredit;

    /**
     * Constructs a CreateAccountRequest with a given customer ID.
     *
     * @param customerId the ID of the customer
     */
    public CreateAccountRequest(Integer customerId) {
        this.customerId = customerId;
    }
}
