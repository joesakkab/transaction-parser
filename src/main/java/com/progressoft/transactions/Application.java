package com.progressoft.transactions;

import com.progressoft.transactions.parsers.TransactionParser;
import com.progressoft.transactions.parsers.TransactionParserFactory;
import com.progressoft.transactions.repositories.H2TransactionRepository;
import com.progressoft.transactions.repositories.TransactionsRepository;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Application {
    private final TransactionParserFactory transactionParserFactory;
    private final TransactionsRepository repository;
    private final String[] listOfFiles = new String[2];

    public Application() {
        transactionParserFactory = new TransactionParserFactory();
        listOfFiles[0] = "transactions.csv";
        listOfFiles[1] = "transactions.xml";
        repository = new H2TransactionRepository();
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        greetings();
        writeDataInChosenFile(sc);
        retrieveFromDB(sc, 0);
    }

    private void greetings() {
        System.out.println("Welcome to the TransactionsParser application!\n" +
                "In this application, you can choose to parse one of two file formats: \n" +
                "1) .csv \n" +
                "2) .xml");
        System.out.println("Type in '1' to parse .csv and '2' to parse .xml or '3' to parse both.");
    }
    private void writeDataInChosenFile(Scanner sc) {
        String response = sc.next().trim();
        switch (response) {
            case "1":
                repository.createTransactionTable();
                parseAndSave(this.listOfFiles[0]);
                break;
            case "2":
                repository.createTransactionTable();
                parseAndSave(this.listOfFiles[1]);
                break;
            case "3":
                repository.createTransactionTable();
                for (String fileName : this.listOfFiles) {
                    parseAndSave(fileName);
                }
                break;
            default:
                System.out.println("Please try again! ");
                writeDataInChosenFile(sc);
        }
    }


    private void retrieveFromDB(Scanner sc, int counter) {
        List<Transaction> sqlSelect = repository.listTransactions();
        if (counter == 0) {
            System.out.println("\n\nThe parser has parsed your file(s) and saved them in an SQL H2 Database! \n" +
                    "Would you like to take a look at the transactions from the table?");
        }

        String response = sc.next().trim();
        if (response.equals("yes")) {
            System.out.println("\n\nThe list of transactions retrieved form the database are: " +
                    listToString(sqlSelect));
        } else if (response.equals("no")) {
            System.out.println("Have a nice day!");
        } else {
            System.out.println("Please try again!");
            retrieveFromDB(sc, counter + 1);
        }
    }

    private void parseAndSave(String fileName) {
        TransactionParser parser = this.transactionParserFactory.createParser(fileName);
        File file = new File("src/main/resources/" + fileName);
        List<Transaction> transactions = parser.parse(file);
        System.out.println("\n\nThe list of transactions are: " + listToString(transactions));

        for (Transaction t : transactions) {
            this.repository.save(t);
        }
    }

    private static String listToString(List<Transaction> transactions) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < transactions.size(); i++) {
            result.append("\n\n").append(i + 1).append(": ").append(transactions.get(i).toString());
        }
        return result.toString();
    }
}

