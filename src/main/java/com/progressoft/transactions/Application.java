package com.progressoft.transactions;

import com.progressoft.transactions.parsers.TransactionParser;
import com.progressoft.transactions.parsers.TransactionParserException;
import com.progressoft.transactions.parsers.TransactionParserFactory;
import com.progressoft.transactions.repositories.H2TransactionRepository;
import com.progressoft.transactions.repositories.TransactionsRepository;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Application {
    private final TransactionParserFactory transactionParserFactory;
    private final TransactionsRepository repository;
    private final JFileChooser fc;

    public Application() {
        transactionParserFactory = new TransactionParserFactory();
        repository = new H2TransactionRepository();
        fc = new JFileChooser();
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        greetings();
        pressEnterToContinue(sc);
        parseAndSave(setupFileChooser());
        retrieveFromDB(sc, 0);
    }

    private void greetings() {
        System.out.println("Welcome to the TransactionsParser application!\n" +
                "In this application, you can choose to parse one of two file formats: \n" +
                "1) .csv \n" +
                "2) .xml");

    }

    private void pressEnterToContinue(Scanner sc)
    {
        System.out.println("Press Enter key to continue...");
        try
        {
            System.in.read();
            sc.nextLine();
        }
        catch(Exception e)
        {}
    }

    private File[] setupFileChooser() {
        fc.addChoosableFileFilter(new FileNameExtensionFilter("*.csv", "csv"));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("*.xml", "xml"));
        fc.setMultiSelectionEnabled(true);
        fc.showOpenDialog(null);
        File[] files = fc.getSelectedFiles();
        if (files.length == 0) {
            throw new TransactionParserException("No file(s) selected!    Have a nice day!");
        }
        return fc.getSelectedFiles();
    }

    private void parseAndSave(File[] files) {
        repository.createTransactionTable();
        for (File file : files) {
            TransactionParser parser = this.transactionParserFactory.createParser(file.getName());
            if (parser == null) {
                throw new TransactionParserException("Make sure the fileType is either .csv or .xml");
            }
            List<Transaction> transactions = parser.parse(file);
            System.out.println("\n\nThe list of transactions are: " + listToString(transactions));

            for (Transaction t : transactions) {
                this.repository.save(t);
            }
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

    private static String listToString(List<Transaction> transactions) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < transactions.size(); i++) {
            result.append("\n\n").append(i + 1).append(": ").append(transactions.get(i).toString());
        }
        return result.toString();
    }
}

