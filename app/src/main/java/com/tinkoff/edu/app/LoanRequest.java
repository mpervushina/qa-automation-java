package com.tinkoff.edu.app;

/**
 * Class,Type -->objects,instances
 */
public class LoanRequest {
    private final int months;
    private final int amount;
    private final LoanType type;

    public LoanRequest(int months, int amount, LoanType type) {
        this.months = months;
        this.amount = amount;
        this.type = type;
    }

    public int getMonths() {
        return months;
    }

    public int getAmount() {
        return amount;
    }
    public LoanType getType(){
        return type;
    }

    public String toString() {
        return "RQ: {" + this.type + "," + this.getAmount() + " for " + this.getMonths() + "}";
    }
}
