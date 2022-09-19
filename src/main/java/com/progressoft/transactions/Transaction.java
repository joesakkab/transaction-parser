package com.progressoft.transactions;

import java.math.BigDecimal;
import java.util.Currency;

public class Transaction {
    private String description;
    private Direction direction;
    private BigDecimal amount;
    private Currency currency;

    private enum Direction {
        CREDIT,
        DEBIT
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String givenDescription) {
        this.description = givenDescription;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(String givenDirection) {
        if (givenDirection.equalsIgnoreCase("Credit")) {
            this.direction = Direction.CREDIT;
        } else if (givenDirection.equalsIgnoreCase("Debit")) {
            this.direction = Direction.DEBIT;
        }
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal givenAmount) {
        this.amount = givenAmount;
    }

    public String getCurrency() {
        return this.currency.getCurrencyCode();
    }

    public void setCurrency(String givenCurrency) {
        this.currency = Currency.getInstance(givenCurrency);
    }

    @Override
    public String toString() {
        String result;
        result = "Description: " + this.getDescription() + "\n   " +
                "Direction: " + this.getDirection() + "\n   " +
                "Amount: " + this.getAmount() + "\n   " +
                "Currency: " + this.getCurrency();
        return result;
    }

}
