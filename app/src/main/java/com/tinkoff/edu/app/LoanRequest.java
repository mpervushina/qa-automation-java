package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.LoanType;

/**
 * Class,Type -->objects,instances
 */
public class LoanRequest {
    private final int months;
    private final int amount;
    private final LoanType type;
    private final String fullname;

    public LoanRequest(int months, int amount, LoanType type, String fullname) {
        this.months = months;
        this.amount = amount;
        this.type = type;
        this.fullname = fullname;
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

    public String getFullname() {
        return fullname;
    }

    public String toString() {
        return "RQ: {" + this.type + "," + this.getAmount() + " for " + this.getMonths() + this.getFullname() + "}";
    }
}
