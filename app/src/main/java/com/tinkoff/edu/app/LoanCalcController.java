package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.ResponseType;
import com.tinkoff.edu.app.exception.FullNameLengthValidationException;

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

    public ResponseType createRequest(LoanRequest request) throws FullNameLengthValidationException {
        if (request == null) throw new IllegalArgumentException();
        if (request.getMonths() <= 0) throw new IllegalArgumentException("Количество месяцев должно быть больше 0");
        if (request.getAmount() <= 0) throw new IllegalArgumentException("Сумма кредита должна быть больше 0");
        if (request.getFullname().length()<10 || request.getFullname().length()>30) throw new FullNameLengthValidationException("Неверное кол-во симолов");

        switch (request.getType()) {
            case PERSON:
                if (request.getMonths() <= 12 && request.getAmount() <= 10000) {
                    return ResponseType.APPROVED;
                } else return ResponseType.DENIED;
            case OOO:
                if (request.getMonths() < 12 && request.getAmount() > 10000) {
                    return ResponseType.APPROVED;
                } else return ResponseType.DENIED;
            case IP:
                return ResponseType.DENIED;
            default:
                throw new RuntimeException("Неизвестный тип клиента");
        }
    }
}
