package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.ResponseType;
import com.tinkoff.edu.app.exception.ApplicatioNotFound;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


public class VeriableLoanCalcRepository implements LoanCalcRepository {
    private ResponseType responseType;
    private UUID uuid;
    private Map<UUID, ResponseType> mapResponse = new HashMap<>();

    public VeriableLoanCalcRepository() {
    }

    /**
     * TODO persists request
     *
     * @return Request Id
     */

    public LoanResponse save(LoanRequest request, ResponseType type) {
        UUID uuid = UUID.randomUUID();
        LoanResponse loanResponse = new LoanResponse(uuid, type);
        mapResponse.put(uuid, type);
        return loanResponse;
    }

    /**
     * Поиск по ид заявки
     */

    public LoanResponse getResponseUuid(UUID uuid) throws ApplicatioNotFound {
        if (mapResponse.containsKey(uuid)) {
            return new LoanResponse(uuid, mapResponse.get(uuid));
        } else throw new ApplicatioNotFound("Заявка не найдена");
    }

    /**
     * Изменение статуса заявки
     */

    public LoanResponse setResponseUuid(UUID uuid, ResponseType type) throws ApplicatioNotFound {
        if (mapResponse.containsKey(uuid)) {
            mapResponse.put(uuid,type);
            return new LoanResponse(uuid, mapResponse.get(uuid));
        } else throw new ApplicatioNotFound("Заявка не найдена");
    }
}



