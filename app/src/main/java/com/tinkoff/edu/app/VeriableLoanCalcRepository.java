package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.LoanType;
import com.tinkoff.edu.app.enums.ResponseType;
import com.tinkoff.edu.app.exception.ApplicatioNotFound;

import java.util.*;


public class VeriableLoanCalcRepository implements LoanCalcRepository {
    private Map<UUID, ResponseType> mapResponse = new HashMap<>();
    private List<UUID> listofOOO = new ArrayList<>();

    public VeriableLoanCalcRepository() {
    }

    /**
     * TODO persists request
     *
     * @return Request Id
     */

    public LoanResponse save(LoanRequest request, ResponseType type, UUID uuid) {
        LoanResponse loanResponse = new LoanResponse(uuid, type);
        mapResponse.put(uuid, type);
        if (request.getType().equals(LoanType.OOO)) {
            listofOOO.add(uuid);
        }
        return loanResponse;
    }

    /**
     * Список заявок по клиентам ООО
     */
    @Override
    public List<UUID> getOOO() {
        return listofOOO;
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
            mapResponse.put(uuid, type);
            return new LoanResponse(uuid, mapResponse.get(uuid));
        } else throw new ApplicatioNotFound("Заявка не найдена");
    }
}



