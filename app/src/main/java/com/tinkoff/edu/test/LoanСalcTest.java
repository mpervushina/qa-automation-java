package com.tinkoff.edu.test;

import com.tinkoff.edu.app.LoanCalcController;

/**
 * Loan Calc Tests
 */
public class LoanСalcTest {
    public static void main(String[] args) {
        int requestId = LoanCalcController.createRequest();
        System.out.println(requestId+" must by 1");
    }
}
