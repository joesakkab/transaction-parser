package com.progressoft.transactions.transactionsrepository;

public class H2TransactionRepositoryException extends RuntimeException {

    public H2TransactionRepositoryException(String message) {
        super(message);
    }

    public H2TransactionRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
