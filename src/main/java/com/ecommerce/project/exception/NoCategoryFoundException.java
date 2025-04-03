package com.ecommerce.project.exception;

public class NoCategoryFoundException extends RuntimeException {

    public NoCategoryFoundException() {
    }

    public NoCategoryFoundException(String message) {
        super(message);
    }
}
