package com.tinkoff.edu.app;

import java.util.UUID;

public class VeriableLoanCalcRepository implements LoanCalcRepository {

    private String requestId;


    public VeriableLoanCalcRepository(String requestId) {
        this.requestId = requestId;

    }

    public VeriableLoanCalcRepository() {
        this.requestId=UUID.randomUUID().toString();
    }

    public String getRequestId() {
        return requestId;
    }

    /**
     * TODO persists request
     *
     * @return Request Id
     */
    @Override
    public String save(LoanRequest request) {

        return this.requestId;
    }
}
