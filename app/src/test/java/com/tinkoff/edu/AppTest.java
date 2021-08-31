package com.tinkoff.edu;


import com.tinkoff.edu.app.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test
     */
    private LoanRequest request;
    private LoanCalcController sut;

    @BeforeEach
    public void unit() {
        request = new LoanRequest(10, 10000, LoanType.PERSON);
    }

    @BeforeEach
    public void sut() {
        VeriableLoanCalcRepository loanCalcRepository = new VeriableLoanCalcRepository();
        sut = new LoanCalcController(new StaticVeriableLoanCalcService(loanCalcRepository));
    }

    @Test
    @Order(1)
    public void shouldGet1WhenFirstRequest() {
        VeriableLoanCalcRepository loanCalcRepository = new VeriableLoanCalcRepository();
        sut = new LoanCalcController(new StaticVeriableLoanCalcService(loanCalcRepository));
        assumeTrue(loanCalcRepository.getRequestId() == 0);
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(1, response.getRequestId());
    }

    @Test
    @Order(2)
    public void shouldGetIncrementedIdWhenAnyCall() {
        final int NOT_DEFAULT_ANY_ID = 2;
        VeriableLoanCalcRepository loanCalcRepository = new VeriableLoanCalcRepository(NOT_DEFAULT_ANY_ID);
        this.sut = new LoanCalcController(new StaticVeriableLoanCalcService(loanCalcRepository));
        assertEquals(3, sut.createRequest(request).getRequestId());
    }

    @Test
    public void shouldGetApprovedWhenValidRequest() {
        int approvingMothes = 10;
        request = new LoanRequest(approvingMothes, 10000, LoanType.PERSON);
        LoanResponse response = sut.createRequest(this.request);
    }

    @Test
    public void shouldGetErrorWhenApplyNullRequest() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    request = null;
                    LoanResponse response = sut.createRequest(this.request);
                });
    }

    @Test
    @DisplayName("Проверка на типе клиента PERSON, сумма равна 0")
    public void shouldGetErrorWhenApplyZeroOrNegativeAmountRequesterPerson() {
        request = new LoanRequest(12, 0, LoanType.PERSON);
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(new LoanResponse(-1, ResponseType.DENIED), response);
    }

    @Test
    @DisplayName("Проверка на типе клиента PERSON,когда значение месяцев равно 0")
    public void shouldGetErrorWhenApplyZeroOrNegativeMonthsRequestPerson() {
        request = new LoanRequest(0, 10000, LoanType.PERSON);
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(new LoanResponse(-1, ResponseType.DENIED), response);
    }


    @Test
    @DisplayName("Проверка на типе клиента ООО, сумма равна 0")
    public void shouldGetErrorWhenApplyZeroOrNegativeAmountRequesterOOO() {
        request = new LoanRequest(12, 0, LoanType.OOO);
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(new LoanResponse(-1, ResponseType.DENIED), response);
    }

    @Test
    @DisplayName("Проверка на типе клиента OOO,когда значение месяцев равно 0")
    public void shouldGetErrorWhenApplyZeroOrNagativeMonthsRequestOOO() {
        request = new LoanRequest(0, 11000, LoanType.OOO);
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(new LoanResponse(-1, ResponseType.DENIED), response);
    }

    @Test
    @DisplayName("Заявка для ИП клиента")
    public void shouldGetDisapprovedClientIp() {
        request = new LoanRequest(12, 10000, LoanType.IP);
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(new LoanResponse(-1, ResponseType.DENIED), response);
    }

    @Test
    @DisplayName("Успешная заявка для клиента с типом ООО")
    public void shouldGetApprovedClientOoo() {
        request = new LoanRequest(10, 15000, LoanType.OOO);
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(new LoanResponse(1, ResponseType.APPROVED), response);
    }

    @Test
    @DisplayName("Клиент ООО, заявка отклонена из-за маленькой суммы")
    public void shouldGetDisapprovedClientOooDueToAmount() {
        request = new LoanRequest(10, 9000, LoanType.OOO);
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(new LoanResponse(-1, ResponseType.DENIED), response);
    }

    @Test
    @DisplayName("Негативный кейс. Клиент ООО, заявка отклонена из-за превышения кол-ва месяцев")
    public void shouldGetDisapprovedClientOooDueToMonths() {
        request = new LoanRequest(13, 11000, LoanType.OOO);
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(new LoanResponse(-1, ResponseType.DENIED), response);
    }

    @Test
    @DisplayName("Клиент OOO, проверка граничных значений месяца и суммы")
    public void checkLimitValueForClientOoo() {
        request = new LoanRequest(12, 10000, LoanType.OOO);
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(new LoanResponse(-1, ResponseType.DENIED), response);
    }

    @Test
    @DisplayName("Клиент PERSON, заявка отклонена из-за превышения кол-ва месяцев")
    public void shouldGetDisapprovedClientPersonDueToMonths() {
        request = new LoanRequest(13, 9000, LoanType.PERSON);
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(new LoanResponse(-1, ResponseType.DENIED), response);
    }

    @Test
    @DisplayName("Клиент PERSON, заявка отклонена из-за превышения суммы")
    public void shouldGetDisapprovedClientPerson() {
        request = new LoanRequest(12, 13000, LoanType.PERSON);
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(new LoanResponse(-1, ResponseType.DENIED), response);
    }

    @Test
    @DisplayName("Клиент PERSON, проверка граничных значений месяца и суммы")
    public void checkLimitValueForClientPersone() {
        request = new LoanRequest(12, 10000, LoanType.PERSON);
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(new LoanResponse(1, ResponseType.APPROVED), response);
    }

    @Test
    @DisplayName("Успешная заявка для клиента с типом Person")
    public void shouldGetApprovedClientPerson() {
        request = new LoanRequest(5, 8000, LoanType.PERSON);
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(new LoanResponse(1, ResponseType.APPROVED), response);
    }
}