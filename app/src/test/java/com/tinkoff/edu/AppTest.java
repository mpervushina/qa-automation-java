package com.tinkoff.edu;


import com.tinkoff.edu.app.*;
import com.tinkoff.edu.app.enums.LoanType;
import com.tinkoff.edu.app.enums.ResponseType;
import com.tinkoff.edu.app.exception.ApplicatioNotFound;
import com.tinkoff.edu.app.exception.FullNameLengthValidationException;
import com.tinkoff.edu.app.exception.ValueFullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.UUID;

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

    @Test
    @DisplayName("Переполнение массива")
    public void checkingUuid() {
        request = new LoanRequest(12, 10000, LoanType.PERSON, "Sidotav Ivan Ivanovich");
        VeriableLoanCalcRepository loanCalcRepository = new VeriableLoanCalcRepository();
        ValueFullException e = assertThrows(ValueFullException.class,
                () -> {
                    for (int i = 0; i < 1001; i++)
                        loanCalcRepository.save(request, ResponseType.APPROVED);
                });
        assertEquals("Массив переполнен", e.getMessage());
    }

    @Test
    @DisplayName("Заявка не найдена")
    public void ApplicationNotFound() {
        request = new LoanRequest(12, 10000, LoanType.PERSON, "Sidotav Ivan Ivanovich");
        VeriableLoanCalcRepository loanCalcRepository = new VeriableLoanCalcRepository();
        ApplicatioNotFound e = assertThrows(ApplicatioNotFound.class,
                () -> {
                    for (int i = 0; i < 100; i++)
                        loanCalcRepository.save(request, ResponseType.APPROVED);
                    loanCalcRepository.getResponseUuid(UUID.randomUUID());
                });
        assertEquals("заявка не найдена", e.getMessage());
    }

    @Test
    @DisplayName("Заявка не найдена при изменение")
    public void UnsuccessfulChangeOfApplication() {
        request = new LoanRequest(12, 10000, LoanType.PERSON, "Sidotav Ivan Ivanovich");
        VeriableLoanCalcRepository loanCalcRepository = new VeriableLoanCalcRepository();
        ApplicatioNotFound e = assertThrows(ApplicatioNotFound.class,
                () -> {
                    for (int i = 0; i < 100; i++)
                        loanCalcRepository.save(request, ResponseType.APPROVED);
                    loanCalcRepository.setResponseUuid(UUID.randomUUID(), ResponseType.DENIED);
                });
        assertEquals("заявка не найдена", e.getMessage());
    }
}