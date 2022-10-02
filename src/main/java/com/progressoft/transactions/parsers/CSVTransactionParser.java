package com.progressoft.transactions.parsers;
import com.progressoft.transactions.processors.FileProcessor;
import com.progressoft.transactions.Transaction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVTransactionParser implements TransactionParser{

    // TODO rename CSVTransactionParser to CsvTransactionParser
    // TODO Keep the parsers job just to parse the file, it should not move the files
    // TODO suggestion: represent Result class that contains the parsing success or failure result
    @Override
    public List<Transaction> parse(File transactionsFile) {
        //TODO use dependency injection for the validator
        //TODO remove the fileProcessor
        ParserValidators validators = new ParserValidators();
        List<Transaction> list = new ArrayList<>();
        FileProcessor fileProcessor = new FileProcessor(transactionsFile);
        String line;
        try {
            Scanner sc = new Scanner(transactionsFile);
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                validators.setFields(line.trim().split(","));
                String message = validators.performAllChecks();
                if (message.equals("")) {
                    list.add(validators.getTransaction());
                } else {
                    fileProcessor.moveFileAndWriteError(message, validators.getIsFileWithErrors());
                    validators.setIsFileWithErrors(true);
                }
            }
            if (!validators.getIsFileWithErrors()) {
                fileProcessor.moveFileToSuccessFolder();
            }
            return list;
        } catch (Exception e) {
            System.out.println("File not found!");
        }
        return null;
    }


}