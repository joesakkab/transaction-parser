package com.progressoft.transactions.parsers;
public class TransactionParserException extends RuntimeException {
    public TransactionParserException(String message) {
        super(message);
    }
    public TransactionParserException(String message, Throwable cause) {
        super(message, cause);
    }
}