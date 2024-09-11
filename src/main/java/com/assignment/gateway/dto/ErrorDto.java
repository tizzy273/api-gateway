package com.assignment.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object for error messages.
 */
@Data
@AllArgsConstructor
public class ErrorDto {

    /**
     * The error message.
     */
    private String message;
}
