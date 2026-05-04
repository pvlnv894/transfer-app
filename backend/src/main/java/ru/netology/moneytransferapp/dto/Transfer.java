package ru.netology.moneytransferapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Transfer(
        @NotBlank
        String cardFromNumber,
        @NotBlank
        String cardFromValidTill,
        @NotBlank
        String cardFromCVV,
        @NotBlank
        String cardToNumber,
        @NotNull
        Amount amount
) {

}


