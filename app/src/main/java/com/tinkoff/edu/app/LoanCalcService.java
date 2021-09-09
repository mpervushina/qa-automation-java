package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.ResponseType;
import com.tinkoff.edu.app.exception.ValueFullException;

public interface LoanCalcService {
    LoanResponse createRequest(LoanRequest request, ResponseType type) throws ValueFullException;
}
