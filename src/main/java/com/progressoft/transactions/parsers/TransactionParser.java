package com.progressoft.transactions.parsers;
import com.progressoft.transactions.processors.Result;

import java.io.File;

public interface TransactionParser {

    //TODO to return Result
    Result parse(File transactionsFile);
}
