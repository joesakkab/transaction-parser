package com.progressoft.transactions.transactionsrepository;

import com.progressoft.transactions.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface TransactionsRepository {
    void save(Transaction t);

    List<Transaction> listTransactions();

    void resetTable();

    void createTransactionTable();
}
