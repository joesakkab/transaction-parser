package com.progressoft.transactions.parsers;
import com.progressoft.transactions.processors.Result;

import java.io.File;
import java.util.Scanner;

public class CsvTransactionParser implements TransactionParser {

    private final ParserValidators VALIDATOR;
    private final Result RESULT;
    public CsvTransactionParser() {
        VALIDATOR = new ParserValidators();
        RESULT = new Result();
    }
    @Override
    public Result parse(File transactionsFile) {
        String line;
        try {
            Scanner sc = new Scanner(transactionsFile);
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                VALIDATOR.setFields(line.trim().split(","));
                updateResult();
            }
            return RESULT;
        } catch (Exception e) {
            System.out.println("File not fond!");
        }
        return null;
    }

    private void updateResult() {
        String message = VALIDATOR.getErrorMessage();
        if (message.equals("")) {
            RESULT.addTransaction(VALIDATOR.getTransaction());
        } else {
            RESULT.addErrorMessage(message);
        }
    }
}