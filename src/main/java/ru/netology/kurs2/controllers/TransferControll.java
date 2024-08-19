package ru.netology.kurs2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.kurs2.models.SuccessResponse;
import ru.netology.kurs2.models.TransferRequest;
import ru.netology.kurs2.services.TransferService;

@RestController
@CrossOrigin(origins = "https://serp-ya.github.io", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
@RequestMapping("/transfer")
public class TransferControll {

    @Autowired
    private TransferService transferService;

    @PostMapping
    public ResponseEntity<Object> transferMoney(@RequestBody TransferRequest request) {
        String operationId = transferService.processTransfer(request);
        SuccessResponse response = new SuccessResponse(operationId);
        return ResponseEntity.ok(response);
    }
}

