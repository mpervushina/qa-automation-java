package com.tinkoff.edu.app;

public class VeriableLoanCalcRepository implements LoanCalcRepository {

    private int requestId;

    public VeriableLoanCalcRepository(int requestId) {
        this.requestId = requestId;
    }

    public VeriableLoanCalcRepository() {
        this(0);
    }

    public int getRequestId() {
        return requestId;
    }

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
