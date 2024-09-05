package ru.netology.kurs2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.netology.kurs2.exc.InvalidDataExc;
import ru.netology.kurs2.exc.TransferExc;
import ru.netology.kurs2.models.OperationRequest;
import ru.netology.kurs2.utils.TransactionLogger;

@Service
public class OperationService {

    @Autowired
    private TransferService transferService;

    public String confirmOperation(OperationRequest request) {
        if (request.getOperationId() == null || request.getOperationId().isEmpty()) {
            TransactionLogger.logOperation(request, "Ошибка");
            throw new InvalidDataExc("Ошибка: отсутствует ID операции");
        }

        boolean success = transferService.confirmOperation(request.getOperationId(), request.getCode());

        if (success) {
            TransactionLogger.logOperation(request, "Успешно");
            return request.getOperationId();
        } else {
            TransactionLogger.logOperation(request, "Ошибка подтверждения");
            throw new TransferExc("Произошла ошибка, попробуйте повторить операцию");
        }
    }
}
