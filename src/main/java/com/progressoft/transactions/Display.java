package com.progressoft.transactions;

import java.util.List;
public class Display {

    public void printTransactionsList(String location, List<Transaction> transactionsList) {
        StringBuilder result = new StringBuilder();
        result.append("The list of transactions from ").append(location).append(" are: ");
        for (int i = 0; i < transactionsList.size(); i++) {
            result.append("\n\n").append(i + 1).append(": ").append(transactionsList.get(i).toString());
        }
        System.out.println(result);
    }
}
