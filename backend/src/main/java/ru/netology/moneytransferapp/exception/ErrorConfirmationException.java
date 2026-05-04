package ru.netology.moneytransferapp.exception;

import lombok.Getter;
import ru.netology.moneytransferapp.enums.ErrorCode;

public class ErrorConfirmationException extends RuntimeException{
    @Getter
    private int id;

    public ErrorConfirmationException(ErrorCode e) {
        super(e.getMessage());
        this.id = e.getCode();
    }
}
