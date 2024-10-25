package com.example.ReceiptProcessor.exceptions;

public class InvalidPriceFormatException extends  RuntimeException{
    public InvalidPriceFormatException(String message) {
        super(message);
    }
}
