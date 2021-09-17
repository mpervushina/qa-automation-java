package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.ResponseType;
import com.tinkoff.edu.app.exception.FullNameLengthValidationException;

import java.util.List;
import java.util.UUID;

/**
 *
 */
public class LoanCalcController {
    /**
     * TODO Validates and logs request.
     */
    private LoanCalcService loanCalcService;

    public LoanCalcController(LoanCalcService loanCalcService) {
        this.loanCalcService = loanCalcService;

    }

    public LoanResponse createRequest(LoanRequest request) throws FullNameLengthValidationException {
        if (request == null) throw new IllegalArgumentException();
        if (request.getMonths() <= 0) throw new IllegalArgumentException("Количество месяцев должно быть больше 0");
        if (request.getAmount() <= 0) throw new IllegalArgumentException("Сумма кредита должна быть больше 0");
        if (request.getFullName().length()<10 || request.getFullName().length()>30) throw new FullNameLengthValidationException("Неверное кол-во симолов");

        switch (request.getType()) {
            case PERSON:
                if (request.getMonths() <= 12 && request.getAmount() <= 10000) {
                    return new LoanResponse(UUID.randomUUID(),ResponseType.APPROVED);
                } else return new LoanResponse(UUID.randomUUID(),ResponseType.DENIED);
            case OOO:
                if (request.getMonths() < 12 && request.getAmount() > 10000) {
                    return new LoanResponse(UUID.randomUUID(),ResponseType.APPROVED);
                } else return new LoanResponse(UUID.randomUUID(),ResponseType.DENIED);
            case IP:
                return new LoanResponse(UUID.randomUUID(),ResponseType.DENIED);
            default:
                throw new RuntimeException("Неизвестный тип клиента");
        }
    }

    public List<UUID> getOOO(){
        return loanCalcService.getOOO();
    }
}
