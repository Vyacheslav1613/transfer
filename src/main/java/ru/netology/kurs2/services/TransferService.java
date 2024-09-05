package ru.netology.kurs2.services;

import org.springframework.stereotype.Service;
import ru.netology.kurs2.exc.InvalidDataExc;
import ru.netology.kurs2.exc.TransferExc;
import ru.netology.kurs2.models.TransferRequest;
import ru.netology.kurs2.utils.TransactionLogger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TransferService {

    private final Map<String, String> operations = new HashMap<>();

    public String processTransfer(TransferRequest request) throws InvalidDataExc {
        if (!validateRequest(request)) {
            TransactionLogger.logTransaction(request, String.valueOf(request.getAmount().getValue() * 0.01), "Данные не прошли валидацию");
            throw new InvalidDataExc("Ошибка ввода данных");
        }

        String operationId = UUID.randomUUID().toString();
        operations.put(operationId, "0000");

        TransactionLogger.logTransaction(request, String.valueOf(request.getAmount().getValue() * 0.01), "Ожидает подтверждения");
        return operationId;
    }

    private boolean validateRequest(TransferRequest request) {
        if (!validNumber(request.getCardFromNumber())) {
            throw new InvalidDataExc("Проверьте номер вашей карты");
        }
        if (!validNumber(request.getCardToNumber())) {
            throw new InvalidDataExc("Проверьте номер карты получателя");
        }
        return request.getCardFromNumber() != null && request.getCardToNumber() != null;
    }

    private boolean validNumber(String cardFromNumber) {
        String[] numbers = cardFromNumber.split("");
        if (numbers.length != 16) {
            return false;
        }
        int controlDigit = Integer.parseInt(numbers[numbers.length - 1]);
        int sum = 0;
        for (int i = numbers.length - 2; i >= 0; i -= 2) {
            int doubled = Integer.parseInt(numbers[i]) * 2;
            sum += doubled > 9 ? doubled - 9 : doubled;
        }
        for (int i = numbers.length - 3; i >= 0; i -= 2) {
            sum += Integer.parseInt(numbers[i]);
        }
        return (sum + controlDigit) % 10 == 0;
    }

    public boolean confirmOperation(String operationId, String code) {
        String storedCode = operations.get(operationId);
        if (storedCode != null && storedCode.equals(code)) {
            operations.remove(operationId);
            return true;
        }
        return false;
    }
}
