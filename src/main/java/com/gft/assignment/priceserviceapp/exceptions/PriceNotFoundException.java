package com.gft.assignment.priceserviceapp.exceptions;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(String message) {
        super(message);
    }
}