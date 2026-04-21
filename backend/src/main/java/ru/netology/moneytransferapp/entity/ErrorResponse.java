package ru.netology.moneytransferapp.entity;

import lombok.Getter;

public class ErrorResponse {
    @Getter
    private int id;
    @Getter
    private String message;

    public ErrorResponse(int id, String message) {
        this.id = id;
        this.message = message;
    }
}
