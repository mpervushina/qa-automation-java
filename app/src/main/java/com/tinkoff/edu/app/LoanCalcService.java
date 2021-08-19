package com.tinkoff.edu.app;


public class LoanCalcService {
    /**
     * TODO Loan Calculation
     */
    public int createRequest(LoanRequest request) {
        //...

        return new LoanCalcRepository().save(request);
    }
}
