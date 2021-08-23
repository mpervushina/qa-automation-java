package com.tinkoff.edu.app;

public class IpNotFriendlyService extends StaticVeriableLoanCalcService{
    public IpNotFriendlyService(LoanCalcRepository loanCalcRepository) {
        super(loanCalcRepository);
    }
    @Override
public int createRequest(LoanRequest request){
        if (request.getType().equals(LoanType.IP)) return -1;
        return super.createRequest(request);
    }
}
