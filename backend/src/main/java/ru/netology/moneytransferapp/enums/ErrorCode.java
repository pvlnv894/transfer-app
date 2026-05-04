package ru.netology.moneytransferapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    INVALID_DATE_FORMAT(1, "Invalid date format"),
    INVALID_CARD_FROM_NUMBER_FORMAT(2, "Invalid cardFromNumber format"),
    INVALID_CARD_TO_NUMBER_FORMAT(3, "Invalid cardToNumber format"),
    INVALID_CVV_FORMAT(4, "Invalid CVV format"),
    INVALID_TRANSFER_AMOUNT(5, "Transfer amount must be greater than 0"),
    INVALID_CODE_FORMAT(6, "Invalid code format"),
    OPERATION_NOT_FOUND(7, "Operation not found"),
    INVALID_CONFIRMATION_CODE(8, "Invalid confirmation code"),
    VALIDATION_ERROR(0, "Fields must not be null");


    private final int code;
    private final String message;
}
