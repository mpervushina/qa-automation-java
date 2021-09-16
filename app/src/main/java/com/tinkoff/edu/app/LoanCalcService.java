package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.ResponseType;
import com.tinkoff.edu.app.exception.ValueFullException;

import java.util.UUID;

public interface LoanCalcService {
    LoanResponse createRequest(LoanRequest request, ResponseType type, UUID uuid) throws ValueFullException;
}
