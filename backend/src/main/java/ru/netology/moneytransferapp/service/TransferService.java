package ru.netology.moneytransferapp.service;

import org.springframework.stereotype.Service;
import ru.netology.moneytransferapp.dto.ConfirmOperation;
import ru.netology.moneytransferapp.dto.Transfer;
import ru.netology.moneytransferapp.entity.*;
import ru.netology.moneytransferapp.enums.ErrorCode;
import ru.netology.moneytransferapp.enums.Status;
import ru.netology.moneytransferapp.exception.ErrorConfirmationException;
import ru.netology.moneytransferapp.exception.ErrorInputDataException;
import ru.netology.moneytransferapp.logging.FileLogWriter;
import ru.netology.moneytransferapp.repository.TransferRepository;
import ru.netology.moneytransferapp.validator.ConfirmOperationValidator;
import ru.netology.moneytransferapp.validator.TransferValidator;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Service
public class TransferService {
    private final TransferRepository repository;

    private final TransferValidator transferValidator;
    private final ConfirmOperationValidator confirmOperationValidator;

    private final FileLogWriter fileLogWriter;

    private static final double KOPECKS_IN_RUBLE = 100;
    private static final double COMMISSION_RATE = 0.01;
    private static final String DEFAULT_CONFIRMATION_CODE = "0000"; //код всегда 0000 для тестового формата

    public TransferService(TransferRepository repository,
                           FileLogWriter fileLogWriter,
                           TransferValidator transferValidator,
                           ConfirmOperationValidator confirmOperationValidator) {
        this.repository = repository;
        this.transferValidator = transferValidator;
        this.confirmOperationValidator = confirmOperationValidator;
        this.fileLogWriter = fileLogWriter;
    }

    public String addTransfer(Transfer transfer) {
        transferValidator.validate(transfer);

        String operationId = UUID.randomUUID().toString();
        repository.saveCode(operationId, DEFAULT_CONFIRMATION_CODE);

        double valueInRubles = transfer.amount().value() / KOPECKS_IN_RUBLE;
        double commission = valueInRubles * COMMISSION_RATE;
        TransferLogEntry transferLogEntry = new TransferLogEntry(
                operationId,
                transfer,
                null,
                null,
                valueInRubles,
                commission,
                null
        );

        repository.saveLog(operationId, transferLogEntry);

        return operationId;
    }

    public String confirmOperation(ConfirmOperation confirmOperation) {
        confirmOperationValidator.validate(confirmOperation);

        String id = confirmOperation.operationId();
        String code = confirmOperation.code();

        TransferLogEntry transferLogEntry = repository.getLog(id);

        if (transferLogEntry == null) {
            throw new ErrorInputDataException(ErrorCode.OPERATION_NOT_FOUND);
        }

        boolean confirmed = code.equals(repository.getCode(id));

        transferLogEntry.setStatus(confirmed ? Status.CONFIRMED : Status.DECLINED);
        transferLogEntry.setDate(LocalDate.now());
        transferLogEntry.setTime(LocalTime.now());

        writeLogSafe(transferLogEntry);
        cleanup(id);

        if (!confirmed) {
            throw new ErrorConfirmationException(ErrorCode.INVALID_CONFIRMATION_CODE);
        }

        return id;
    }

    private void writeLogSafe(TransferLogEntry log) {
        try {
            fileLogWriter.writeLog(log);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void cleanup(String id) {
        repository.removeCode(id);
        repository.removeLog(id);
    }
}
