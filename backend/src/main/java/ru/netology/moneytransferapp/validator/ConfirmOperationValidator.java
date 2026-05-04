package ru.netology.moneytransferapp.validator;

import org.springframework.stereotype.Component;
import ru.netology.moneytransferapp.dto.ConfirmOperation;
import ru.netology.moneytransferapp.enums.ErrorCode;
import ru.netology.moneytransferapp.exception.ErrorInputDataException;

@Component
public class ConfirmOperationValidator {
    private static final String CODE_PATTERN = "\\d{4}";

    public void validate(ConfirmOperation confirmOperation) {
        String code = confirmOperation.code();

        if (!code.matches(CODE_PATTERN)) {
            throw new ErrorInputDataException(ErrorCode.INVALID_CODE_FORMAT);
        }
    }
}
