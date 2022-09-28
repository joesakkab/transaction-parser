package com.progressoft.transactions.parsers;

import com.progressoft.transactions.Transaction;

import java.io.File;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ParserValidators {

    private final File transactionsFile;
    private final String fileFormat;
    private String[] tokens;
    private boolean fileHasError = false;
    private PrintStream writeErrors;
    private int transactionCounter;

    public ParserValidators(File transactionsFile, String fileFormat) {
        this.transactionsFile = transactionsFile;
        this.fileFormat = fileFormat;
        this.transactionCounter = 1;
    }

    public void setTokens(String[] tokens) {
        this.tokens = tokens;
    }

    public void incrementTransactionCounter() {
        transactionCounter++;
    }

    // CHECK 1: File format
    public boolean validateFileFormat() {
        if (!transactionsFile.getName().endsWith(fileFormat)) {
            moveFileAndWriteError("Please make sure the file is in " + fileFormat.substring(1) +
                    " format (ends with " + fileFormat + ")");
            return false;
        }
        return true;
    }

    // CHECK 2: Length of tokens list
    private boolean validateLengthOfTokensList() {
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].trim();
        }
        return tokens.length == 4;
    }

    // CHECK 3: empty fields
    private int validateEmptyFields() {
        int idx = 0;
        for (String token: tokens) {
            if (token.trim().equals("")) {
                return idx;
            }
            idx++;
        }
        return -1;
    }

    // CHECK 4: valid direction
    private boolean validateTransactionDirection() {
        String direction = tokens[1];
        return direction.equalsIgnoreCase("debit") || direction.equalsIgnoreCase("credit");
    }

    // CHECK 5: valid amount
    private BigDecimal validateTransactionAmount() {
        try {
            return new BigDecimal(tokens[2]);
        } catch (Exception e) {
            return null;
        }
    }

    // CHECK 6: valid currency
    private boolean validateTransactionCurrency() {
        try {
            new Transaction().setCurrency(tokens[3]);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ALL CHECKS
    public Transaction performAllChecks() {
        String[] fields = {"Description", "Direction", "Amount", "Currency"};
        if (!validateLengthOfTokensList()) {
            moveFileAndWriteError("Missing field");
        } else if (validateEmptyFields() != -1) {
            moveFileAndWriteError(fields[validateEmptyFields()] + " field is empty");
        } else if (!validateTransactionDirection()) {
            moveFileAndWriteError("Direction must be 'Debit' or 'Credit'");
        } else if (validateTransactionAmount() == null) {
            moveFileAndWriteError("Amount field must be numeric");
        } else if (!validateTransactionCurrency()) {
            moveFileAndWriteError("Currency field is not valid");
        } else {
            return new Transaction(tokens[0], tokens[1], validateTransactionAmount(), tokens[3]);
        }
        return null;
    }

    public void moveFileAndWriteError(String message) {
        try {
            if (!fileHasError) {
                String name = transactionsFile.getName();
                String errorFileName = name.substring(0, name.indexOf(".")) + "_error.txt";
                String directoryPath = transactionsFile.getParent();
                File errorFile = new File(directoryPath + "/errors/" + errorFileName);
                writeErrors =  new PrintStream(errorFile);
                Files.move(Paths.get(transactionsFile.getPath()),Paths.get(directoryPath + "/errors/" + name));
                fileHasError = true;
            }
            if (!validateFileFormat()) {
                writeErrors.append(("Invalid file format. Please use either .csv or .xml files"));
            } else {
                writeErrors.append("\ntransaction " + transactionCounter + ": " + message);
            }
        } catch (Exception e) {
            System.out.println("Exception here!");
        }
    }

    public void moveFileToSuccessFolder() {
        if (!fileHasError) {
            String name = transactionsFile.getName();
            String directoryPath = transactionsFile.getParent();
            try {
                Files.move(Paths.get(transactionsFile.getPath()),Paths.get(directoryPath + "/successes/" + name));
            } catch (Exception ignored) {
            }
        }
    }
}
