package ru.netology.moneytransferapp.exception;

import lombok.Getter;

public class ErrorInputDataException extends RuntimeException{
    @Getter
    private int id;

    public ErrorInputDataException(int id, String msg) {
        super(msg);
        this.id = id;
    }
}
