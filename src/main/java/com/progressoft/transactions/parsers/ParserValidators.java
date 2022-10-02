package com.progressoft.transactions.parsers;

import com.progressoft.transactions.Transaction;
import java.math.BigDecimal;

//TODO it understands only the fields validations and no more
public class ParserValidators {
    private String[] fields;
    private boolean isFileWithErrors;
    private int transactionCounter;

    private void incrementTransactionCounter() {
        transactionCounter++;
    }

    // CHECK 1: Length of tokens list
    private boolean isCorrectLength() {
        return fields.length == 4;
    }

    // CHECK 2: empty fields
    private int getEmptyFieldIndex() {
        int idx = 0;
        for (String token: fields) {
            if (token.trim().equals("")) {
                return idx;
            }
            idx++;
        }
        return -1;
    }

    // CHECK 3: valid direction
    private boolean isCorrectDirection() {
        String direction = fields[1];
        return direction.equalsIgnoreCase("debit") || direction.equalsIgnoreCase("credit");
    }

    // CHECK 4: valid amount
    private boolean isCorrectAmount() {
        try {
            new BigDecimal(fields[2]);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // CHECK 5: valid currency
    private boolean isCorrectCurrency() {
        try {
            new Transaction().setCurrency(fields[3]);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Constructor
    public ParserValidators() {
        this.transactionCounter = 0;
        setIsFileWithErrors(false);
    }

    // ALL CHECKS
    public String performAllChecks() {
        String[] fieldNames = {"Description", "Direction", "Amount", "Currency"};
        int idx = getEmptyFieldIndex();
        incrementTransactionCounter();
//        setIsFileWithErrors(true);
        if (!isCorrectLength()) {
            return "transaction " + transactionCounter + ": Missing field";
        } else if (idx != -1) {
           return "transaction " + transactionCounter + ": " + fieldNames[idx] + " field is empty";
        } else if (!isCorrectDirection()) {
            return "transaction " + transactionCounter + ": Direction must be 'Debit' or 'Credit'";
        } else if (!isCorrectAmount()) {
            return "transaction " + transactionCounter + ": Amount field must be numeric";
        } else if (!isCorrectCurrency()) {
            return "transaction " + transactionCounter + ": Currency field is not valid";
        }
        return "";
    }

    // Getter method for Transaction. Usually called after performAllChecks
    public Transaction getTransaction() {
        return new Transaction(fields[0], fields[1], new BigDecimal(fields[2]), fields[3]);
    }

    // Get idFileWithErrors boolean
    public boolean getIsFileWithErrors() {
        return isFileWithErrors;
    }

    // Set isFileWithErrors boolean
    public void setIsFileWithErrors(boolean bool) {
        isFileWithErrors = bool;
    }

    // Set the tokens field of the class.
    public void setFields(String[] fields) {
        this.fields = fields;
    }

}
