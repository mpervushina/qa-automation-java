package com.tinkoff.edu.app;

public class StaticVeriableLoanCalcService implements LoanCalcService {
    private LoanCalcRepository loanCalcRepository;

    public StaticVeriableLoanCalcService(LoanCalcRepository loanCalcRepository) {
        this.loanCalcRepository = loanCalcRepository;
    }

    /**
     * TODO Loan Calculation
     */
    public LoanResponse createRequest(LoanRequest request,ResponseType type) throws ValueFullException {

        return loanCalcRepository.save(request,type);
    }
}