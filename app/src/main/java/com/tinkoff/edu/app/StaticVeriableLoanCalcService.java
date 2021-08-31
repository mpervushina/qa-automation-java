package com.tinkoff.edu.app;

public class StaticVeriableLoanCalcService implements LoanCalcService {
    private LoanCalcRepository loanCalcRepository;

    public StaticVeriableLoanCalcService(LoanCalcRepository loanCalcRepository) {
        this.loanCalcRepository = loanCalcRepository;
    }

    /**
     * TODO Loan Calculation
     */
    public int createRequest(LoanRequest request) {

        return loanCalcRepository.save(request);
    }
}