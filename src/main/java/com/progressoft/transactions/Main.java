package com.progressoft.transactions;

import com.progressoft.transactions.processors.FileProcessor;
import com.progressoft.transactions.processors.InitialSetup;
import com.progressoft.transactions.parsers.TransactionParser;
import com.progressoft.transactions.parsers.TransactionParserFactory;
import com.progressoft.transactions.repositories.H2TransactionRepository;
import com.progressoft.transactions.repositories.TransactionsRepository;

import java.io.File;
import java.util.List;

public class Main {

    //TODO the main has to be shorter, suggestion: let the main just calls the FileProcessor and let it handle the rest
    public static void main(String[] args) {
        //TODO move this logic inside the FileProcessor, use also dependency injection for factory ,repository ,and the directory
        TransactionParserFactory transactionParserFactory = new TransactionParserFactory();
        TransactionsRepository repository = new H2TransactionRepository();
        repository.createTransactionTable();
        String directory = args[0];
//        String directory = "/home/joe/IdeaProjects/transaction-files";
        System.out.println("start processing files in " + directory);
        InitialSetup setup = new InitialSetup(directory);
        File[] listOfFiles = setup.getListOfFiles();
        setup.createErrorAndSuccessDirectories();

        // Loop through list of files and convert content to transactions
        for (File file: listOfFiles) {
            TransactionParser parser = transactionParserFactory.createParser(file.getName());
            if (parser == null) {
                new FileProcessor(file).moveInvalidFileType();
                continue;
            }

            // Given that file format is checked, then parse the content of the file into transactions
            List<Transaction> transactions = parser.parse(file);
            System.out.println("For the file: " + file.getName());
            System.out.println("The list of transactions are: \n" + listToString(transactions));

            for (Transaction t : transactions) {
                repository.save(t);
            }
            transactions.clear();
        }

        List<Transaction> sqlSelect = repository.listTransactions();
        System.out.println("The list of transactions retrieved form the database are: \n" +
                listToString(sqlSelect));

    }

    public static String listToString(List<Transaction> transactions) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < transactions.size(); i++) {
            result.append("\n\n").append(i + 1).append(": ").append(transactions.get(i).toString());
        }
        return result.toString();
    }
}