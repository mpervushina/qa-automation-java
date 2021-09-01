package com.tinkoff.edu.test;

import com.tinkoff.edu.app.*;

/**
 * Loan Calc Tests
 */
public class LoanСalcTest {
    public void main(String[] args) {
        LoanRequest request = new LoanRequest(12, 1_000, LoanType.IP);
        LoanCalcRepository loanCalcRepository = new VeriableLoanCalcRepository();
        LoanCalcController loanCalcController = new LoanCalcController(new StaticVeriableLoanCalcService(loanCalcRepository));
        int requestId = loanCalcController.createRequest(request).getRequestId();
        LoanResponse response=new LoanResponse(requestId,ResponseType.APPROVED);
        System.out.println(request);
        System.out.println(response.getRequestId() + " must be 1");
    }
}