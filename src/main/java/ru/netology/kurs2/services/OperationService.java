package ru.netology.kurs2.services;

import org.springframework.stereotype.Service;
import ru.netology.kurs2.exc.InvalidDataExc;
import ru.netology.kurs2.exc.TransferExc;
import ru.netology.kurs2.models.OperationRequest;
import ru.netology.kurs2.utils.TransactionLogger;

import java.util.Random;

@Service
public class OperationService {

    public String confirmOperation(OperationRequest request) {

        if (request.getOperationId() == null || request.getOperationId().isEmpty()) {
            TransactionLogger.logOperation(request, "Ошибка");
            throw new InvalidDataExc("Ошибка");
        }

        if (new Random().nextInt(100) < 70) {
            TransactionLogger.logOperation(request, "Успешно");
            return request.getOperationId();
        } else {
            TransactionLogger.logOperation(request, "Error");
            throw new TransferExc("Произошла ошибка, попробуйте повторить операцию");
        }
    }
}
