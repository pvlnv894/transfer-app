package ru.netology.moneytransferapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class ConfirmOperation {
    @NotNull
    @Getter
    private String operationId;
    @NotNull
    @Getter
    private String code;

    public ConfirmOperation(String operationId, String code) {
        this.operationId = operationId;
        this.code = code;
    }

    public ConfirmOperation() {
    }
}
