package ru.netology.kurs2.exc;

public class InvalidDataExc extends RuntimeException {
    public InvalidDataExc(String message) {
        super(message);
    }
}
