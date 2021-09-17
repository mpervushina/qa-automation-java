package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.ResponseType;
import com.tinkoff.edu.app.exception.ValueFullException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface LoanCalcRepository {
    LoanResponse save(LoanRequest request, ResponseType type,UUID uuid) throws ValueFullException;
    List<UUID> getOOO();
}
