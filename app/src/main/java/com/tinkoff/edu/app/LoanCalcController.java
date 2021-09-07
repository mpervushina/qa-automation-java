package com.tinkoff.edu.app;

/**
 *
 */
public class LoanCalcController {
    /**
     * TODO Validates and logs request.
     */
    private LoanCalcService loanCalcService;

    public LoanCalcController(LoanCalcService loanCalcService) {
        this.loanCalcService = loanCalcService;

    }

    public LoanResponse createRequest(LoanRequest request) {
        if (request == null) throw new IllegalArgumentException();

        if (request.getMonths() > 0 && request.getMonths() <= 12 && request.getType().equals(LoanType.PERSON) && request.getAmount() > 0 && request.getAmount() <= 10000) {
            return new LoanResponse( ResponseType.APPROVED);
        }

        if (request.getMonths() > 0 && request.getMonths() < 12 && request.getAmount() > 10000 && request.getType().equals(LoanType.OOO)) {
            return new LoanResponse( ResponseType.APPROVED);
        } else {
            return new LoanResponse( ResponseType.DENIED);
        }
    }
}
