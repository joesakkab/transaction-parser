package com.progressoft.transactions.parsers;

import com.progressoft.transactions.Transaction;

import java.io.File;
import java.math.BigDecimal;

public class ParserValidators {

    public Transaction checkErrorsAndConvert(String[] tokens) {
        checkTransactionFields(tokens);
        checkIfNumeric(tokens[2]);
        return new Transaction(tokens[0], tokens[1], checkIfNumeric(tokens[2]), tokens[3]);
    }

    public void validateFileFormat(File transactionsFile, String fileformat) {
        if (!transactionsFile.getName().endsWith(fileformat)) {
            throw new TransactionParserException("Please make sure file is in csv format (ends with .csv)");
        }
    }
    private void checkTransactionFields(String[] tokens) {
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].trim();
            if (tokens[i].equals("")) {
                throw new TransactionParserException("Please make sure none of the fields are white spaces");
            }
        }
        if (tokens.length != 4){
            throw new TransactionParserException("Please make sure none of the fields are empty");
        }
    }

    private BigDecimal checkIfNumeric(String token) {
        try {
            return new BigDecimal(token);
        } catch (Exception e){
            throw new TransactionParserException("Please make sure transaction amount is numeric");
        }
    }
}
