package com.tinkoff.edu;


import com.tinkoff.edu.app.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    public void init() {
        request = new LoanRequest(10, 10000, LoanType.PERSON, "Sidotav Ivan Ivanovich");
        VeriableLoanCalcRepository loanCalcRepository = new VeriableLoanCalcRepository();
        sut = new LoanCalcController(new StaticVeriableLoanCalcService(loanCalcRepository));
    }
    
    @Test
    public void shouldGetErrorWhenApplyNullRequest() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    request = null;
                    ResponseType response = sut.createRequest(this.request);
                });
    }

    @Test
    @DisplayName("Проверка на типе клиента PERSON, сумма равна 0")
    public void shouldGetErrorWhenApplyZeroOrNegativeAmountRequesterPerson() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    request = new LoanRequest(12, 0, LoanType.PERSON, "Sidotav Ivan Ivanovich");
                    ResponseType response = sut.createRequest(this.request);
                    assertEquals(ResponseType.DENIED, response);
                });
    }

    @Test
    @DisplayName("Проверка на типе клиента PERSON,когда значение месяцев равно 0")
    public void shouldGetErrorWhenApplyZeroOrNegativeMonthsRequestPerson() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    request = new LoanRequest(0, 10000, LoanType.PERSON, "Sidotav Ivan Ivanovich");
                    ResponseType response = sut.createRequest(this.request);
                    assertEquals(ResponseType.DENIED, response);
                });
    }

    @Test
    @DisplayName("Проверка, когда полное имя по длине меньше")
    public void FullNameMoreLength() {
        assertThrows(FullNameLengthValidationException.class,
                () -> {
                    request = new LoanRequest(2, 10000, LoanType.PERSON, "1234567890qwertyuiop555555asdfg");
                    ResponseType response = sut.createRequest(this.request);
                });
    }

    @Test
    @DisplayName("Проверка когда полное имя по длине меньше")
    public void FullNameSmallerLength() {
        assertThrows(FullNameLengthValidationException.class,
                () -> {
                    request = new LoanRequest(2, 10000, LoanType.PERSON, "uuu");
                    ResponseType response = sut.createRequest(this.request);
                });
    }

    @Test
    @DisplayName("Заявка для ИП клиента")
    public void shouldGetDisapprovedClientIp() throws FullNameLengthValidationException {
        request = new LoanRequest(12, 10000, LoanType.IP, "Sidotav Ivan Ivanovich");
        ResponseType response = sut.createRequest(this.request);
        assertEquals(ResponseType.DENIED, response);
    }

    @Test
    @DisplayName("Успешная заявка для клиента с типом ООО")
    public void shouldGetApprovedClientOoo() throws FullNameLengthValidationException {
        request = new LoanRequest(10, 15000, LoanType.OOO, "Sidotav Ivan Ivanovich");
        ResponseType response = sut.createRequest(this.request);
        assertEquals(ResponseType.APPROVED, response);
    }

    @Test
    @DisplayName("Клиент ООО, заявка отклонена из-за маленькой суммы")
    public void shouldGetDisapprovedClientOooDueToAmount() throws FullNameLengthValidationException {
        request = new LoanRequest(10, 9000, LoanType.OOO, "Sidotav Ivan Ivanovich");
        ResponseType response = sut.createRequest(this.request);
        assertEquals(ResponseType.DENIED, response);
    }

    @Test
    @DisplayName("Негативный кейс. Клиент ООО, заявка отклонена из-за превышения кол-ва месяцев")
    public void shouldGetDisapprovedClientOooDueToMonths() throws FullNameLengthValidationException {
        request = new LoanRequest(13, 11000, LoanType.OOO, "Sidotav Ivan Ivanovich");
        ResponseType response = sut.createRequest(this.request);
        assertEquals(ResponseType.DENIED, response);
    }

    @Test
    @DisplayName("Клиент OOO, проверка граничных значений месяца и суммы")
    public void checkLimitValueForClientOoo() throws FullNameLengthValidationException {
        request = new LoanRequest(12, 10000, LoanType.OOO, "Sidotav Ivan Ivanovich");
        ResponseType response = sut.createRequest(this.request);
        assertEquals(ResponseType.DENIED, response);
    }

    @Test
    @DisplayName("Клиент PERSON, заявка отклонена из-за превышения кол-ва месяцев")
    public void shouldGetDisapprovedClientPersonDueToMonths() throws FullNameLengthValidationException {
        request = new LoanRequest(13, 9000, LoanType.PERSON, "Sidotav Ivan Ivanovich");
        ResponseType response = sut.createRequest(this.request);
        assertEquals(ResponseType.DENIED, response);
    }

    @Test
    @DisplayName("Клиент PERSON, заявка отклонена из-за превышения суммы")
    public void shouldGetDisapprovedClientPerson() throws FullNameLengthValidationException {
        request = new LoanRequest(12, 13000, LoanType.PERSON, "Sidotav Ivan Ivanovich");
        ResponseType response = sut.createRequest(this.request);
        assertEquals(ResponseType.DENIED, response);
    }

    @Test
    @DisplayName("Клиент PERSON, проверка граничных значений месяца и суммы")
    public void checkLimitValueForClientPersone() throws FullNameLengthValidationException {
        request = new LoanRequest(12, 10000, LoanType.PERSON, "Sidotav Ivan Ivanovich");
        ResponseType response = sut.createRequest(this.request);
        assertEquals(ResponseType.APPROVED, response);
    }

    @Test
    @DisplayName("Успешная заявка для клиента с типом Person")
    public void shouldGetApprovedClientPerson() throws FullNameLengthValidationException {
        request = new LoanRequest(5, 8000, LoanType.PERSON, "Sidotav Ivan Ivanovich");
        ResponseType response = sut.createRequest(this.request);
        assertEquals(ResponseType.APPROVED, response);
    }

    @Test
    @DisplayName("Заявка на неизвестного клиента")
    public void shouldGetExceptionClientUnknown() {
        assertThrows(NullPointerException.class,
                () -> {
                    request = new LoanRequest(5, 8000, null, "Sidotav Ivan Ivanovich");
                    ResponseType response = sut.createRequest(this.request);
                    assertEquals(ResponseType.APPROVED, response);
                });
    }
}