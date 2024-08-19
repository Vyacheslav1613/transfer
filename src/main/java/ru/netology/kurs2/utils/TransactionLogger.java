package ru.netology.kurs2.utils;

import ru.netology.kurs2.models.OperationRequest;
import ru.netology.kurs2.models.TransferRequest;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TransactionLogger {
    private static final Logger logger = Logger.getLogger(TransactionLogger.class.getName());
    private static FileHandler fileHandler;

    static {
        try {
            fileHandler = new FileHandler("transactions.log", true);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logTransaction(TransferRequest request, String commission, String result) {
        double commissions = Double.parseDouble(commission)*0.01;
        logger.info(String.format("Date/Time: %tc, Card From: %s, Card To: %s, Amount: %s %s, Commission: %s, Result: %s",
                System.currentTimeMillis(),
                request.getCardFromNumber(),
                request.getCardToNumber(),
                request.getAmount().getValue()*0.01,
                request.getAmount().getCurrency(),
                commissions,
                result));
    }

    public static void logOperation(OperationRequest request, String result) {
        logger.info(String.format("Date/Time: %tc, Operation ID: %s, Confirmation Code: %s, Result: %s",
                System.currentTimeMillis(),
                request.getOperationId(),
                request.getCode(),
                result));
    }
}
