package com.progressoft.transactions.parsers;
import com.progressoft.transactions.processors.Result;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class CsvTransactionParser implements TransactionParser {

    private final ParserValidators validator_;
    public CsvTransactionParser(ParserValidators validator) {
        validator_ = validator;
    }
    @Override
    public Result parse(File transactionsFile) {
        String line;
        Result result = new Result();
        try {
            Scanner sc = new Scanner(transactionsFile);
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                validator_.setFields(line.trim().split(","));
                updateResult(result);
            }
            return result;
        } catch (Exception e) {
            throw new TransactionParserException(e.getMessage());
        }
    }

    private void updateResult(Result result) {
        String message = validator_.getErrorMessage();
        if (message.equals("")) {
            result.addTransaction(validator_.getTransaction());
        } else {
            result.addErrorMessage(message);
        }
    }
}