package com.progressoft.transactions.processors;

import com.progressoft.transactions.Transaction;

import java.util.ArrayList;
import java.util.List;

public class Result {

    private final List<Transaction> listOfTransactions;
    private final List<String> listOfErrorMessages;

    public Result() {
        listOfTransactions = new ArrayList<>();
        listOfErrorMessages = new ArrayList<>();

    }

    public void addTransaction(Transaction transaction) {
        listOfTransactions.add(transaction);
    }

    public void addErrorMessage(String message) {
        listOfErrorMessages.add(message);
    }

    public List<Transaction> getListOfTransactions() {
        return listOfTransactions;
    }

    public List<String> getListOfErrorMessages() {
        return listOfErrorMessages;
    }

    public boolean isFileWithError() {
        return listOfErrorMessages.size() != 0;
    }
}
