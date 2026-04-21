package ru.netology.moneytransferapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class Transfer {
    @NotNull
    @Getter
    private String cardFromNumber;
    @NotNull
    @Getter
    private String cardFromValidTill;
    @NotNull
    @Getter
    private String cardFromCVV;
    @NotNull
    @Getter
    private String cardToNumber;
    @NotNull
    @Getter
    private Amount amount;

    public Transfer(String cardFromNumber, String cardFromValidTill, String cardFromCVV,
                    String cardToNumber, Amount amount) {
        this.cardFromNumber = cardFromNumber;
        this.cardToNumber = cardToNumber;
        this.cardFromCVV = cardFromCVV;
        this.cardFromValidTill = cardFromValidTill;
        this.amount = amount;
    }

    public Transfer() {
    }
}


