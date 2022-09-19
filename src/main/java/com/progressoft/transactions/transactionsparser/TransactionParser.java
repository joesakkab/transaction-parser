package com.progressoft.transactions.transactionsparser;
import com.progressoft.transactions.Transaction;

import java.io.File;
import java.util.List;

public interface TransactionParser {
    List<Transaction> parse(File transactionsFile);
}
