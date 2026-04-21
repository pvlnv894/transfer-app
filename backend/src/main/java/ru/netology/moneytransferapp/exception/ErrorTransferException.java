package ru.netology.moneytransferapp.exception;

import lombok.Getter;

public class ErrorTransferException extends RuntimeException{
    @Getter
    private int id;

    public ErrorTransferException(int id, String msg) {
        super(msg);
        this.id = id;
    }
}
