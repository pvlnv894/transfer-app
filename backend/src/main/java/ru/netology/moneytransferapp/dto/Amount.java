package ru.netology.moneytransferapp.dto;

import lombok.Getter;
import lombok.Setter;

public class Amount {
    @Getter
    private Double value;
    @Getter
    private String currency;

    public Amount(Double value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public Amount() {
    }

    public void setValue(Double value) {
        this.value = value / 100;
    }
}
