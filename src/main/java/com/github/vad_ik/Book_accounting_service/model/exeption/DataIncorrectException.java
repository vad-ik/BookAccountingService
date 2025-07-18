package com.github.vad_ik.Book_accounting_service.model.exeption;

public class DataIncorrectException extends RuntimeException {
    public DataIncorrectException(String message) {
        super(message);
    }
}