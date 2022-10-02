package com.progressoft.transactions.parsers;
import com.progressoft.transactions.processors.Result;

import java.io.File;

public interface TransactionParser {

    Result parse(File transactionsFile);
}
