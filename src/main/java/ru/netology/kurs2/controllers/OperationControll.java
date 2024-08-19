package ru.netology.kurs2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.kurs2.models.OperationRequest;
import ru.netology.kurs2.models.SuccessResponse;
import ru.netology.kurs2.services.OperationService;

@RestController
@CrossOrigin(origins = "https://serp-ya.github.io", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
@RequestMapping("/confirmOperation")
public class OperationControll {

    @Autowired
    private OperationService operationService;

    @PostMapping
    public ResponseEntity<Object> transferMoney(@RequestBody OperationRequest request) {
        String operationId = operationService.confirmOperation(request);
        SuccessResponse response = new SuccessResponse(operationId);
        return ResponseEntity.ok(response);
    }
}
