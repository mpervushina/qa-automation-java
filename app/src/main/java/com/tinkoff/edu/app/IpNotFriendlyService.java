package com.tinkoff.edu.app;

public class IpNotFriendlyService extends StaticVeriableLoanCalcService {
    public IpNotFriendlyService(LoanCalcRepository loanCalcRepository) {
        super(loanCalcRepository);
    }

    @Override
    public String createRequest(LoanRequest request) {
        if (request.getType().equals(LoanType.IP)) return "";
        return super.createRequest(request);
    }
}
