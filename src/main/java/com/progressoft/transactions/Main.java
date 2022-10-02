package com.progressoft.transactions;

import com.progressoft.transactions.processors.TransactionFileProcessor;

public class Main {
    public static void main(String[] args) {
        TransactionFileProcessor transactionFileProcessor = new TransactionFileProcessor();
//         SAMPLE DIRECTORY: "/home/joe/IdeaProjects/transaction-files"
//        transactionFileProcessor.setDIRECTORY("/home/joe/IdeaProjects/transaction-files");
        transactionFileProcessor.setDIRECTORY(args[0]);
        transactionFileProcessor.process();
    }
}