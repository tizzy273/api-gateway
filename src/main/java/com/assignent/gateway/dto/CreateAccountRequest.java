package com.assignent.gateway.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class CreateAccountRequest {

    @NotNull
    private Integer customerId;

    private Float initialCredit;
}
