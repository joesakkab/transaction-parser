package com.progressoft.transactions.parsers;

public class TransactionParserFactory {
    public TransactionParser createParser(String fileType) {
        if (fileType == null) {
            return null;
        }
        if (fileType.endsWith(".csv")) {
            return new CSVTransactionParser();
        } else if (fileType.endsWith(".xml")) {
            return new XMLTransactionParser();
        }
        return null;
    }
}
