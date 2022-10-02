package com.progressoft.transactions;

import com.progressoft.transactions.parsers.TransactionParserFactory;
import com.progressoft.transactions.processors.TransactionFileProcessor;
import com.progressoft.transactions.repositories.H2TransactionRepository;

public class Main {
    public static void main(String[] args) {
        TransactionFileProcessor transactionFileProcessor = new TransactionFileProcessor(
                new TransactionParserFactory(), new H2TransactionRepository(), new Display(),
                args[0]
        );
//         SAMPLE DIRECTORY: "/home/joe/IdeaProjects/transaction-files"
        transactionFileProcessor.process();
    }
}