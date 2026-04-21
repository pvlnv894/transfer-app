package ru.netology.moneytransferapp.entity;

import lombok.Getter;
import lombok.Setter;
import ru.netology.moneytransferapp.dto.Transfer;

import java.time.LocalDate;
import java.time.LocalTime;

public class TransferLogEntry {
    @Getter
    private String operationId;
    @Getter
    private Transfer transfer;
    @Getter
    @Setter
    private String date;
    @Getter
    @Setter
    private String time;
    @Getter
    private String commission;
    @Getter
    @Setter
    private Status status;

    public TransferLogEntry(String operationId, Transfer transfer, Status status) {
        this.operationId = operationId;
        this.transfer = transfer;
        this.date = LocalDate.now().toString();
        this.time = LocalTime.now().toString();
        this.commission = String.valueOf((double) transfer.getAmount().getValue() / 100);
        this.status = status;
    }
}
