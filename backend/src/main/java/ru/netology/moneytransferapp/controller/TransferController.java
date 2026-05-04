package ru.netology.moneytransferapp.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.netology.moneytransferapp.dto.ConfirmOperation;
import ru.netology.moneytransferapp.dto.Transfer;
import ru.netology.moneytransferapp.service.TransferService;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TransferController {
    private final TransferService service;

    public static final String OPERATION_ID = "operationId";

    public TransferController(TransferService service) {
        this.service = service;
    }

    @PostMapping("/transfer")
    public Map<String, String> addTransfer(@Valid @RequestBody Transfer transfer) {
        String id = service.addTransfer(transfer);
        return Map.of(OPERATION_ID, id);
    }

    @PostMapping("/confirmOperation")
    public Map<String, String> confirmOperation(@Valid @RequestBody ConfirmOperation confirmOperation) {
        String id = service.confirmOperation(confirmOperation);
        return Map.of(OPERATION_ID, id);
    }
}

