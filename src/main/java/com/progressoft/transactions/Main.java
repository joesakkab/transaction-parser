package com.progressoft.transactions;

import com.progressoft.transactions.parsers.TransactionParser;
import com.progressoft.transactions.parsers.TransactionParserFactory;
import com.progressoft.transactions.repositories.H2TransactionRepository;
import com.progressoft.transactions.repositories.TransactionsRepository;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TransactionParserFactory transactionParserFactory = new TransactionParserFactory();
        TransactionsRepository repository = new H2TransactionRepository();
        repository.createTransactionTable();
        String directory = args[0];
//        String directory = "/home/joe/IdeaProjects/transaction-files";
        System.out.println("start processing files in " + directory);

        //Create file to get list of file names
        File first = new File(directory);
        String[] listOfFileNames = first.list();

        // Create directory called errors.
        File errorDirectory = new File(directory + "/errors");
        if (errorDirectory.mkdir()) {
            System.out.println("Folder for 'errors' successfully created!");
        } else {
            System.out.println("Error found!");
        }

        // Create a directory called successes
        File successDirectory = new File(directory + "/successes");
        if (successDirectory.mkdir()) {
            System.out.println("Folder for 'successes' successfully created!");
        } else {
            System.out.println("Error found!");
        }

        //Convert list of file names to list of files
        File[] listOfFiles = new File[listOfFileNames.length];
        for (int i = 0; i < listOfFileNames.length; i++) {
            listOfFiles[i] = new File(directory + "/" + listOfFileNames[i]);
        }
        // Loop through list of files and convert content to transactions
        for (File file: listOfFiles) {
            TransactionParser parser = transactionParserFactory.createParser(file.getName());
            if (parser == null) {
                continue;
            }
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