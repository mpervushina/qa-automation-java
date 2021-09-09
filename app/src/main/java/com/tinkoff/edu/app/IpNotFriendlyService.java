package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.LoanType;
import com.tinkoff.edu.app.enums.ResponseType;
import com.tinkoff.edu.app.exception.ValueFullException;

public class IpNotFriendlyService extends StaticVeriableLoanCalcService {
    public IpNotFriendlyService(LoanCalcRepository loanCalcRepository) {
        super(loanCalcRepository);
    }

    @Override
    public LoanResponse createRequest(LoanRequest request, ResponseType type) throws ValueFullException {
        if (request.getType().equals(LoanType.IP)) {
            return super.createRequest(request, type);
        }
        else return super.createRequest(request, type);
    }
}