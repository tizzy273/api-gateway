package com.assignent.gateway.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {

    @NotNull
    private Integer customerId;

    private Float initialCredit;

    public CreateAccountRequest(Integer customerId) {

        this.customerId = customerId;
    }
}
