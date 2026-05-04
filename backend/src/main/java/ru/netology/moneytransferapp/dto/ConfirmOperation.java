package ru.netology.moneytransferapp.dto;

import jakarta.validation.constraints.NotBlank;

public record ConfirmOperation (
        @NotBlank
        String operationId,
        @NotBlank
        String code) {
}
