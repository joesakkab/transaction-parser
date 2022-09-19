package com.progressoft.transactions.transactionsrepository;

import com.progressoft.transactions.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface TransactionsRepository {
    void save(Transaction t) throws SQLException;

    List<Transaction> listTransactions() throws SQLException;

    void resetTable() throws SQLException;

    void createTransactionTable() throws SQLException;
}
