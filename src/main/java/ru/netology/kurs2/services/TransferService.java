package ru.netology.kurs2.services;

import org.springframework.stereotype.Service;
import ru.netology.kurs2.exc.InvalidDataExc;
import ru.netology.kurs2.exc.TransferExc;
import ru.netology.kurs2.models.TransferRequest;
import ru.netology.kurs2.utils.TransactionLogger;

import java.util.UUID;

@Service
public class TransferService {

    public String processTransfer(TransferRequest request) throws InvalidDataExc, TransferExc {
        if (!validateRequest(request)) {
            TransactionLogger.logTransaction(request, String.valueOf(request.getAmount().getValue() * 0.01), "Данные не прошли валидацию");
            throw new InvalidDataExc("Ошибка ввода данных");
        }
        return performTransfer(request);
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


    private String performTransfer(TransferRequest request) throws TransferExc {

        if (Integer.parseInt(request.getCardFromCVV()) < 900) {
            TransactionLogger.logTransaction(request, String.valueOf(request.getAmount().getValue()*0.01), "Успешно");
            return UUID.randomUUID().toString();

        } else {
            TransactionLogger.logTransaction(request, String.valueOf(request.getAmount().getValue() * 0.01), "Ошибка ввода данных");
            throw new TransferExc("Неверный CVC!");
        }
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
}