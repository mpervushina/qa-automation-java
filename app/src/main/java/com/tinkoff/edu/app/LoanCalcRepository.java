package com.tinkoff.edu.app;

public class LoanCalcRepository {
    private static int requestId;

    /**
     * TODO persists request
     *
     * @return Request Id
     */
    public static int save() {
        int localVar = ++requestId;
        return localVar;
    }
}