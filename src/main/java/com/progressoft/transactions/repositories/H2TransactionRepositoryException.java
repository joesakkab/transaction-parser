package com.progressoft.transactions.repositories;

public class H2TransactionRepositoryException extends RuntimeException {

    public H2TransactionRepositoryException(String message) {
        super(message);
    }

    public H2TransactionRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
