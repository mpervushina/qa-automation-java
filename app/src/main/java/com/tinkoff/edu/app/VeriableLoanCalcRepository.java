package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.ResponseType;
import com.tinkoff.edu.app.exception.ApplicatioNotFound;
import com.tinkoff.edu.app.exception.ValueFullException;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;


public class VeriableLoanCalcRepository implements LoanCalcRepository {

    private LoanResponse[] loanResponses;
    private int value = 0;

    public VeriableLoanCalcRepository() {

        loanResponses = new LoanResponse[1000];
    }

    /**
     * TODO persists request
     *
     * @return Request Id
     */
    public LoanResponse save(LoanRequest request, ResponseType type) throws ValueFullException {
        if (value < 1000) {
            UUID uuid = UUID.randomUUID();
            LoanResponse loanResponse = new LoanResponse(uuid, type);
            loanResponses[value++] = loanResponse;
            return loanResponse;
        }
        throw new ValueFullException("Массив переполнен");
    }

    /**
     * Поиск по ид заявки
     */
    public LoanResponse getResponseUuid(UUID uuid) throws ApplicatioNotFound {
        for (int i = 0; i < value; i++) {
            if (Objects.equals(loanResponses[i].getUuid(), uuid)) {
                return loanResponses[i];
            }
        }
        throw new ApplicatioNotFound("заявка не найдена");
    }

    /**
     * Изменение статуса заявки
     */
    public ResponseType setResponseUuid(UUID uuid, ResponseType type) throws ApplicatioNotFound {
        for (int i = 0; i < value; i++) {
            if (Objects.equals(loanResponses[i].getUuid(), uuid)) {
                loanResponses[i].setType(type);
                return loanResponses[i].getType();
            }
        }
        throw new ApplicatioNotFound("заявка не найдена");
    }
}



