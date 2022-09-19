package com.progressoft.transactions.transactionsparser;
import com.progressoft.transactions.Transaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVTransactionParser implements TransactionParser{

    // Implementation of TransactionParser interface.
    @Override
    public List<Transaction> parse(File transactionsFile) {
        ParserValidators validators = new ParserValidators();
        validators.validateFileFormat(transactionsFile, ".csv");
        try {
            Scanner sc = new Scanner(transactionsFile);
            List<Transaction> list = new ArrayList<>();
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String[] tokens = line.split(",");
                list.add(validators.checkErrorsAndConvert(tokens));
            }
            return list;
        } catch (FileNotFoundException e) {
            throw new TransactionParserException("Please make sure file is valid");
        }
    }


}