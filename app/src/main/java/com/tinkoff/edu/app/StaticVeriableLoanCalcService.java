package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.ResponseType;
import com.tinkoff.edu.app.exception.ValueFullException;

import java.util.List;
import java.util.UUID;

public class StaticVeriableLoanCalcService implements LoanCalcService {
    private LoanCalcRepository loanCalcRepository;

    public StaticVeriableLoanCalcService(LoanCalcRepository loanCalcRepository) {
        this.loanCalcRepository = loanCalcRepository;
    }

    /**
     * TODO Loan Calculation
     */
    public LoanResponse createRequest(LoanRequest request, ResponseType type, UUID uuid) throws ValueFullException {

        return loanCalcRepository.save(request, type, uuid);
    }

    public List<UUID> getOOO() {
        return loanCalcRepository.getOOO();
    }
}