package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.ResponseType;
import com.tinkoff.edu.app.exception.ValueFullException;

public interface LoanCalcRepository {
    LoanResponse save(LoanRequest request, ResponseType type) throws ValueFullException;

}
