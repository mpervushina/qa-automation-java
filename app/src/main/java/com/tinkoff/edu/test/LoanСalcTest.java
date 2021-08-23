package com.tinkoff.edu.test;

import com.tinkoff.edu.app.*;

/**
 * Loan Calc Tests
 */
public class LoanСalcTest {
    public static void main(String[] args) {
        LoanRequest request = new LoanRequest(12, 1_000, LoanType.IP);
        LoanCalcRepository loanCalcRepository=new StaticVeriableLoanCalcRepository();
        LoanCalcController loanCalcController=new LoanCalcController(new StaticVeriableLoanCalcService(loanCalcRepository));
        int requestId = loanCalcController.createRequest(request);
        System.out.println(request);
        System.out.println(requestId + " must by 1");
    }
}