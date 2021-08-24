package com.tinkoff.edu.app;

public class StaticVeriableLoanCalcRepository implements LoanCalcRepository {
    private static int requestId;

    /**
     * TODO persists request
     *
     * @return Request Id
     */
    @Override
    public int save(LoanRequest request) {

        return ++requestId;
    }
}
