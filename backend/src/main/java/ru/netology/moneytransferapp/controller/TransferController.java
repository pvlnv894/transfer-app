package ru.netology.moneytransferapp.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.netology.moneytransferapp.dto.ConfirmOperation;
import ru.netology.moneytransferapp.entity.ErrorResponse;
import ru.netology.moneytransferapp.dto.Transfer;
import ru.netology.moneytransferapp.exception.ErrorConfirmationException;
import ru.netology.moneytransferapp.exception.ErrorInputDataException;
import ru.netology.moneytransferapp.exception.ErrorTransferException;
import ru.netology.moneytransferapp.service.TransferService;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TransferController {
    private final TransferService service;

    public TransferController(TransferService service) {
        this.service = service;
    }

    @PostMapping("/transfer")
    public Map<String, String> addTransfer(@Valid @RequestBody Transfer transfer) {
        String id = service.addTransfer(transfer);
        return Map.of("operationId", id);
    }

    @PostMapping("/confirmOperation")
    public Map<String, String> confirmOperation(@Valid @RequestBody ConfirmOperation confirmOperation) {
        String id = service.confirmOperation(confirmOperation);
        return Map.of("operationId", id);
    }

    @ExceptionHandler(ErrorConfirmationException.class)
    public ResponseEntity<ErrorResponse> tnfHandler(ErrorConfirmationException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(e.getId(), e.getMessage()));
    }


    @ExceptionHandler(ErrorTransferException.class)
    public ResponseEntity<ErrorResponse> tnfHandler(ErrorTransferException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(e.getId(), e.getMessage()));
    }

    @ExceptionHandler(ErrorInputDataException.class)
    public ResponseEntity<ErrorResponse> tnfHandler(ErrorInputDataException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getId(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> vHandler(MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(0, "Fields must not be null"));
    }
}

