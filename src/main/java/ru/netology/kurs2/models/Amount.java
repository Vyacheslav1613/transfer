package ru.netology.kurs2.models;

public class Amount {
    private Integer value;
    private String currency;

    public Amount() {
    }

    public Amount(Integer value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
