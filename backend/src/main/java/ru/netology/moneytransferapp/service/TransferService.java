package ru.netology.moneytransferapp.service;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferapp.dto.ConfirmOperation;
import ru.netology.moneytransferapp.dto.Transfer;
import ru.netology.moneytransferapp.entity.*;
import ru.netology.moneytransferapp.exception.ErrorConfirmationException;
import ru.netology.moneytransferapp.exception.ErrorInputDataException;
import ru.netology.moneytransferapp.exception.ErrorTransferException;
import ru.netology.moneytransferapp.repository.TransferRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.UUID;

@Service
public class TransferService {
    private final Gson gson = new Gson();
    private final Path path;

    private final TransferRepository repository;

    public TransferService(TransferRepository repository) {
        this.repository = repository;

        this.path = Paths.get("./logs/transfer-log.jsonl");
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String addTransfer(Transfer transfer) {
        String[] parts = transfer.getCardFromValidTill().split("/");
        if (parts.length != 2) {
            throw new ErrorInputDataException(1, "Invalid date format");
        }

        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]) + 2000;

        if (month > 12 || month < 1) {
            throw new ErrorInputDataException(1, "Invalid date format");
        }

        YearMonth cardDate = YearMonth.of(year, month);
        YearMonth now = YearMonth.now();

        if (cardDate.isBefore(now)) {
            throw new ErrorInputDataException(1, "Invalid date format");
        }

        String from = transfer.getCardFromNumber();
        String to = transfer.getCardToNumber();
        String cvv = transfer.getCardFromCVV();
        Long amount = transfer.getAmount().getValue();

        if (!from.matches("\\d{16}")) {
            throw new ErrorInputDataException(2, "Invalid cardFromNumber format");
        }
        if (!to.matches("\\d{16}")) {
            throw new ErrorInputDataException(3, "Invalid cardToNumber format");
        }
        if (!cvv.matches("\\d{3}")) {
            throw new ErrorInputDataException(4, "Invalid CVV format");
        }
        if (amount <= 0) {
            throw new ErrorTransferException(5, "Transfer amount must be greater than 0");
        }

        String operationId = UUID.randomUUID().toString();

        repository.saveCode(operationId, "0000"); //код всегда 0000 для тестового формата

        TransferLogEntry transferLogEntry = new TransferLogEntry(operationId, transfer, null);
        repository.saveLog(operationId, transferLogEntry);

        return operationId;
    }

    public void writeLog(TransferLogEntry transferLogEntry) throws IOException {
        try (FileWriter file = new FileWriter(path.toFile(), true)) {
            file.write(gson.toJson(transferLogEntry));
            file.write(System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String confirmOperation(ConfirmOperation confirmOperation) {
        String id = confirmOperation.getOperationId();
        String code = confirmOperation.getCode();

        if (!code.matches("\\d{4}")) {
            throw new ErrorInputDataException(6, "Invalid code format");
        }

        TransferLogEntry transferLogEntry = repository.getLog(id);

        if (transferLogEntry == null) {
            throw new ErrorInputDataException(7, "Operation not found");
        }

        transferLogEntry.setDate(LocalDate.now().toString());
        transferLogEntry.setTime(LocalTime.now().toString());

        if (code.equals(repository.getCode(id))) {
            transferLogEntry.setStatus(Status.CONFIRMED);

            try {
                writeLog(transferLogEntry);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }

            repository.removeCode(id);
            repository.removeLog(id);
            return id;
        }

        transferLogEntry.setStatus(Status.DECLINED);

        try {
            writeLog(transferLogEntry);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        repository.removeCode(id);
        repository.removeLog(id);
        throw new ErrorConfirmationException(8, "Invalid confirmation code");
    }
}
