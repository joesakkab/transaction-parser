package com.progressoft.transactions.parsers;
import com.progressoft.transactions.Transaction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVTransactionParser implements TransactionParser{

    // Implementation of TransactionParser interface.
    @Override
    public List<Transaction> parse(File transactionsFile) {
        ParserValidators validators = new ParserValidators(transactionsFile, ".csv");
        List<Transaction> list = new ArrayList<>();
        String line;
        if (!validators.validateFileFormat()) {
            return list;
        }
        try {
            Scanner sc = new Scanner(transactionsFile);
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                validators.setTokens(line.split(","));
                Transaction currentTransaction = validators.performAllChecks();
                if (currentTransaction != null) {
                    list.add(currentTransaction);
                }
                validators.incrementTransactionCounter();
            }
            validators.moveFileToSuccessFolder();
            return list;
        } catch (Exception e) {
            System.out.println("Error has occurred");
//            throw new TransactionParserException("FileNotFoundException");
        }
        return null;
    }


}