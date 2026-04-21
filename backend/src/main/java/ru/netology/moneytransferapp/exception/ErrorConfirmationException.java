package ru.netology.moneytransferapp.exception;

import lombok.Getter;

public class ErrorConfirmationException extends RuntimeException{
    @Getter
    private int id;

    public ErrorConfirmationException(int id, String msg) {
        super(msg);
        this.id = id;
    }
}
