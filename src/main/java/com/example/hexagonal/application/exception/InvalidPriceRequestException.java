package com.example.hexagonal.application.exception;

public class InvalidPriceRequestException extends RuntimeException{
    public InvalidPriceRequestException(String message) {
        super(message);
    }
}
