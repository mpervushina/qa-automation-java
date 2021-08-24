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

    public int createRequest(LoanRequest request) {

        //param validation
        //log request
        return loanCalcService.createRequest(request);
    }
}
