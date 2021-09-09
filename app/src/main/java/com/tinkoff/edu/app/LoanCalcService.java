package com.tinkoff.edu.app;

public interface LoanCalcService {
    Object createRequest(LoanRequest request, ResponseType type) throws ValueFullException;
}
