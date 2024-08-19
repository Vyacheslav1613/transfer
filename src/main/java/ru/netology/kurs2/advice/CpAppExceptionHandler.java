package ru.netology.kurs2.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.kurs2.exc.InvalidDataExc;
import ru.netology.kurs2.exc.TransferExc;
import ru.netology.kurs2.models.ErrorResponse;

@RestControllerAdvice
@CrossOrigin(origins = "https://serp-ya.github.io", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class CpAppExceptionHandler {

    @ExceptionHandler(InvalidDataExc.class)
    public ResponseEntity<Object> handleInvalidDataException(InvalidDataExc ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransferExc.class)
    public ResponseEntity<Object> handleTransferException(TransferExc ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("An unexpected error has occurred", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}