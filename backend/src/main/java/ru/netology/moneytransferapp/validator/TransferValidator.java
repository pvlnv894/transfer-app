package ru.netology.moneytransferapp.validator;

import org.springframework.stereotype.Component;
import ru.netology.moneytransferapp.dto.Transfer;
import ru.netology.moneytransferapp.enums.ErrorCode;
import ru.netology.moneytransferapp.exception.ErrorInputDataException;
import ru.netology.moneytransferapp.exception.ErrorTransferException;

import java.time.YearMonth;

@Component
public class TransferValidator {
    private static final String DATE_SEPARATOR = "/";
    private static final int YEAR_BASE = 2000;
    private static final String CARD_NUMBER_PATTERN = "\\d{16}";
    private static final String CVV_PATTERN = "\\d{3}";

    public void validate(Transfer transfer) {
        String[] parts = transfer.cardFromValidTill().split(DATE_SEPARATOR);
        if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank()) {
            throw new ErrorInputDataException(ErrorCode.INVALID_DATE_FORMAT);
        }

        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]) + YEAR_BASE;

        if (month > 12 || month < 1) {
            throw new ErrorInputDataException(ErrorCode.INVALID_DATE_FORMAT);
        }

        YearMonth cardDate = YearMonth.of(year, month);
        YearMonth now = YearMonth.now();

        if (cardDate.isBefore(now)) {
            throw new ErrorInputDataException(ErrorCode.INVALID_DATE_FORMAT);
        }

        String from = transfer.cardFromNumber();
        String to = transfer.cardToNumber();
        String cvv = transfer.cardFromCVV();
        Double amountValue = transfer.amount().value();

        if (!from.matches(CARD_NUMBER_PATTERN)) {
            throw new ErrorInputDataException(ErrorCode.INVALID_CARD_FROM_NUMBER_FORMAT);
        }
        if (!to.matches(CARD_NUMBER_PATTERN)) {
            throw new ErrorInputDataException(ErrorCode.INVALID_CARD_TO_NUMBER_FORMAT);
        }
        if (!cvv.matches(CVV_PATTERN)) {
            throw new ErrorInputDataException(ErrorCode.INVALID_CVV_FORMAT);
        }
        if (amountValue <= 0) {
            throw new ErrorTransferException(ErrorCode.INVALID_TRANSFER_AMOUNT);
        }
    }
}
