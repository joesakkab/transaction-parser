package com.progressoft.transactions;

import com.progressoft.transactions.parsers.TransactionParser;
import com.progressoft.transactions.parsers.TransactionParserFactory;
import com.progressoft.transactions.repositories.H2TransactionRepository;
import com.progressoft.transactions.repositories.TransactionsRepository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TransactionParserFactory transactionParserFactory = new TransactionParserFactory();
        TransactionsRepository repository = new H2TransactionRepository();
        repository.createTransactionTable();

        String filePath = args[0];
        System.out.println("start processing file " + filePath);
        Path path = Paths.get(filePath);
        TransactionParser parser = transactionParserFactory.createParser(path.getFileName().toString());
        List<Transaction> transactions = parser.parse(path.toFile());
        System.out.println("The list of transactions are: \n" + listToString(transactions));

        for (Transaction t : transactions) {
            repository.save(t);
        }

        List<Transaction> sqlSelect = repository.listTransactions();
        System.out.println("The list of transactions retrevied form the database are: \n" +
                listToString(sqlSelect));

    }

    public static String listToString(List<Transaction> transactions) {
        String result = "";
        for (int i = 0; i < transactions.size(); i++) {
            result = result + "\n\n" + (i + 1) + ": " + transactions.get(i).toString();
        }
        return result;
    }
}