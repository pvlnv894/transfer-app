package ru.netology.moneytransferapp.exception;

import lombok.Getter;
import ru.netology.moneytransferapp.enums.ErrorCode;

public class ErrorInputDataException extends RuntimeException{
    @Getter
    private int id;

    public ErrorInputDataException(ErrorCode e) {
        super(e.getMessage());
        this.id = e.getCode();
    }
}
