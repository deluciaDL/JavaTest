package com.example.hexagonal.domain.model;

public enum Currency {

    EUR("EUR", "€", "Euro"), USD("USD", "$", "United Stated Dollar");

    private final String code;
    private final String symbol;
    private final String name;

    Currency(String code, String symbol, String name) {
        this.code = code;
        this.symbol = symbol;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }
}
