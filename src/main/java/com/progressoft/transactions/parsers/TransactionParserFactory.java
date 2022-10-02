package com.progressoft.transactions.parsers;

public class TransactionParserFactory {
    public TransactionParser createParser(String fileType) {
        if (fileType == null) {
            return null;
        }
        if (fileType.endsWith(".csv")) {
            return new CsvTransactionParser(new ParserValidators());
        } else if (fileType.endsWith(".xml")) {
            return new XmlTransactionParser(new ParserValidators());
        }
        return null;
    }
}
