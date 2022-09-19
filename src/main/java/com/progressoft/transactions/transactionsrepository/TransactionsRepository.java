package com.progressoft.transactions.transactionsrepository;
import com.progressoft.transactions.Transaction;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface TransactionsRepository {
    void save(Transaction t);
    List<Transaction> listTransactions() throws SQLException;
    void resetTable(String action);
    void createTransactionTable();




}
