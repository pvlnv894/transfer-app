package ru.netology.moneytransferapp.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.moneytransferapp.dto.ErrorResponse;
import ru.netology.moneytransferapp.enums.ErrorCode;
import ru.netology.moneytransferapp.exception.ErrorConfirmationException;
import ru.netology.moneytransferapp.exception.ErrorInputDataException;
import ru.netology.moneytransferapp.exception.ErrorTransferException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(ErrorConfirmationException.class)
    public ResponseEntity<ErrorResponse> HandleErrorConfirmationException(ErrorConfirmationException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(e.getId(), e.getMessage()));
    }

    @ExceptionHandler(ErrorTransferException.class)
    public ResponseEntity<ErrorResponse> HandleErrorTransferException(ErrorTransferException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(e.getId(), e.getMessage()));
    }

    @ExceptionHandler(ErrorInputDataException.class)
    public ResponseEntity<ErrorResponse> HandleErrorInputDataException(ErrorInputDataException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getId(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> HandleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ErrorCode.VALIDATION_ERROR.getCode(), ErrorCode.VALIDATION_ERROR.getMessage()));
    }
}
