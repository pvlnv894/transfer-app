package ru.netology.moneytransferapp.exception;

import lombok.Getter;
import ru.netology.moneytransferapp.enums.ErrorCode;

public class ErrorTransferException extends RuntimeException{
    @Getter
    private int id;

    public ErrorTransferException(ErrorCode e) {
        super(e.getMessage());
        this.id = e.getCode();
    }
}
