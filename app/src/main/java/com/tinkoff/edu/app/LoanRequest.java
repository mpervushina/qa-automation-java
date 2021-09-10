package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.LoanType;

/**
 * Class,Type -->objects,instances
 */
public class LoanRequest {
    private final int months;
    private final int amount;
    private final LoanType type;
    private final String fullName;

    public LoanRequest(int months, int amount, LoanType type, String fullName) {
        this.months = months;
        this.amount = amount;
        this.type = type;
        this.fullName = fullName;
    }

    public int getMonths() {
        return months;
    }

    public int getAmount() {
        return amount;
    }

    public LoanType getType() {
        return type;
    }

    public String getFullName() {
        return fullName;
    }

    public String toString() {
        return "RQ: {" + this.type + "," + this.getAmount() + " for " + this.getMonths() + this.getFullName() + "}";
    }
}
