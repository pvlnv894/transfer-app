package ru.netology.moneytransferapp.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import ru.netology.moneytransferapp.dto.Transfer;
import ru.netology.moneytransferapp.enums.Status;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@JsonPropertyOrder({
        "operationId",
        "date",
        "time",
        "cardFromNumber",
        "cardFromValidTill",
        "cardFromCVV",
        "cardToNumber",
        "valueInRubles",
        "commission",
        "currency",
        "status"
})
public class TransferLogEntry {
    private String operationId;
    private String cardFromNumber;
    private String cardFromValidTill;
    private String cardFromCVV;
    private String cardToNumber;
    private double valueInRubles;
    private String currency;
    private LocalDate date;
    private LocalTime time;
    private double commission;
    private Status status;

    public TransferLogEntry(
            String operationId,
            Transfer transfer,
            LocalDate date,
            LocalTime time,
            double valueInRubles,
            double commission,
            Status status
    ) {
        this.operationId = operationId;
        this.cardFromNumber = transfer.cardFromNumber();
        this.cardFromValidTill = transfer.cardFromValidTill();
        this.cardFromCVV = transfer.cardFromCVV();
        this.cardToNumber = transfer.cardToNumber();
        this.valueInRubles = valueInRubles;
        this.currency = transfer.amount().currency();
        this.date = date;
        this.time = time;
        this.commission = commission;
        this.status = status;
    }
}
