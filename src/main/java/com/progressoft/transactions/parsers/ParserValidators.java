package com.progressoft.transactions.parsers;

import com.progressoft.transactions.Transaction;
import java.math.BigDecimal;

public class ParserValidators {
    private String[] fields;
    private int transactionCounter;

    public ParserValidators() {
        this.transactionCounter = 0;
    }

    public String getErrorMessage() {
        String[] fieldNames = {"Description", "Direction", "Amount", "Currency"};
        int idx = getEmptyFieldIndex();
        incrementTransactionCounter();
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

    public Transaction getTransaction() {
        return new Transaction(fields[0], fields[1], new BigDecimal(fields[2]), fields[3]);
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    private void incrementTransactionCounter() {
        transactionCounter++;
    }

    private boolean isCorrectLength() {
        return fields.length == 4;
    }

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

    private boolean isCorrectDirection() {
        String direction = fields[1];
        return direction.equalsIgnoreCase("debit") || direction.equalsIgnoreCase("credit");
    }

    private boolean isCorrectAmount() {
        try {
            new BigDecimal(fields[2]);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isCorrectCurrency() {
        try {
            new Transaction().setCurrency(fields[3]);
            return true;
        } catch (Exception e) {
            return false;
        }
    }



}
