package com.tinkoff.edu.test;

import com.tinkoff.edu.app.*;
import com.tinkoff.edu.app.enums.LoanType;
import com.tinkoff.edu.app.enums.ResponseType;

import java.util.UUID;

/**
 * Loan Calc Tests
 */
public class LoanСalcTest {
    public void main(String[] args) {
        LoanRequest request = new LoanRequest(12, 1_000, LoanType.IP,"");
        LoanCalcRepository loanCalcRepository = new VeriableLoanCalcRepository();
        LoanCalcController loanCalcController = new LoanCalcController(new StaticVeriableLoanCalcService(loanCalcRepository));
        LoanResponse response=new LoanResponse(UUID.randomUUID(), ResponseType.APPROVED);
        System.out.println(request);
        System.out.println(response + " must be 1");
    }
}