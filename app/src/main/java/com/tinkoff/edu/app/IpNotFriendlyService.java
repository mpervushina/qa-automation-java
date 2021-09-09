package com.tinkoff.edu.app;

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