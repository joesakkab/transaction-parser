package com.progressoft.transactions.parsers;
import com.progressoft.transactions.processors.FileProcessor;
import com.progressoft.transactions.Transaction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVTransactionParser implements TransactionParser{

    // Implementation of TransactionParser interface.
    @Override
    public List<Transaction> parse(File transactionsFile) {
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