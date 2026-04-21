package ru.netology.moneytransferapp.dto;

import lombok.Getter;

public class Amount {
    @Getter
    private Long value;
    @Getter
    private String currency;

    public Amount(Long value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public Amount() {
    }
}
