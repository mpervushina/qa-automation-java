package com.tinkoff.edu;


import com.tinkoff.edu.app.*;
import com.tinkoff.edu.app.enums.LoanType;
import com.tinkoff.edu.app.enums.ResponseType;
import com.tinkoff.edu.app.exception.ApplicatioNotFound;
import com.tinkoff.edu.app.exception.FullNameLengthValidationException;
import com.tinkoff.edu.app.exception.ValueFullException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
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
                    LoanResponse response = sut.createRequest(this.request);
                });
    }

    @Test
    @DisplayName("Проверка на типе клиента PERSON, сумма равна 0")
    public void shouldGetErrorWhenApplyZeroOrNegativeAmountRequesterPerson() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    request = new LoanRequest(12, 0, LoanType.PERSON, "Sidotav Ivan Ivanovich");
                    LoanResponse response = sut.createRequest(this.request);
                    assertEquals(ResponseType.DENIED, response);
                });
    }

    @Test
    @DisplayName("Проверка на типе клиента PERSON,когда значение месяцев равно 0")
    public void shouldGetErrorWhenApplyZeroOrNegativeMonthsRequestPerson() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    request = new LoanRequest(0, 10000, LoanType.PERSON, "Sidotav Ivan Ivanovich");
                    LoanResponse response = sut.createRequest(this.request);
                    assertEquals(ResponseType.DENIED, response);
                });
    }

    @Test
    @DisplayName("Проверка, когда полное имя по длине меньше")
    public void FullNameMoreLength() {
        assertThrows(FullNameLengthValidationException.class,
                () -> {
                    request = new LoanRequest(2, 10000, LoanType.PERSON, "1234567890qwertyuiop555555asdfg");
                    LoanResponse response = sut.createRequest(this.request);
                });
    }

    @Test
    @DisplayName("Проверка когда полное имя по длине меньше")
    public void FullNameSmallerLength() {
        assertThrows(FullNameLengthValidationException.class,
                () -> {
                    request = new LoanRequest(2, 10000, LoanType.PERSON, "uuu");
                    LoanResponse response = sut.createRequest(this.request);
                });
    }

    @Test
    @DisplayName("Заявка для ИП клиента")
    public void shouldGetDisapprovedClientIp() throws FullNameLengthValidationException {
        request = new LoanRequest(12, 10000, LoanType.IP, "Sidotav Ivan Ivanovich");
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(ResponseType.DENIED, response.getType());
    }

    @Test
    @DisplayName("Успешная заявка для клиента с типом ООО")
    public void shouldGetApprovedClientOoo() throws FullNameLengthValidationException {
        request = new LoanRequest(10, 15000, LoanType.OOO, "Sidotav Ivan Ivanovich");
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(ResponseType.APPROVED, response.getType());
    }

    @Test
    @DisplayName("Клиент ООО, заявка отклонена из-за маленькой суммы")
    public void shouldGetDisapprovedClientOooDueToAmount() throws FullNameLengthValidationException {
        request = new LoanRequest(10, 9000, LoanType.OOO, "Sidotav Ivan Ivanovich");
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(ResponseType.DENIED, response.getType());
    }

    @Test
    @DisplayName("Негативный кейс. Клиент ООО, заявка отклонена из-за превышения кол-ва месяцев")

    public void shouldGetDisapprovedClientOooDueToMonths() throws FullNameLengthValidationException {
        request = new LoanRequest(13, 11000, LoanType.OOO, "Sidotav Ivan Ivanovich");
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(ResponseType.DENIED, response.getType());
    }

    @Test
    @DisplayName("Клиент OOO, проверка граничных значений месяца и суммы")
    public void checkLimitValueForClientOoo() throws FullNameLengthValidationException {
        request = new LoanRequest(12, 10000, LoanType.OOO, "Sidotav Ivan Ivanovich");
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(ResponseType.DENIED, response.getType());
    }

    @Test
    @DisplayName("Клиент PERSON, заявка отклонена из-за превышения кол-ва месяцев")
    public void shouldGetDisapprovedClientPersonDueToMonths() throws FullNameLengthValidationException {
        request = new LoanRequest(13, 9000, LoanType.PERSON, "Sidotav Ivan Ivanovich");
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(ResponseType.DENIED, response.getType());
    }

    @Test
    @DisplayName("Клиент PERSON, заявка отклонена из-за превышения суммы")
    public void shouldGetDisapprovedClientPerson() throws FullNameLengthValidationException {
        request = new LoanRequest(12, 13000, LoanType.PERSON, "Sidotav Ivan Ivanovich");
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(ResponseType.DENIED, response.getType());
    }

    @Test
    @DisplayName("Клиент PERSON, проверка граничных значений месяца и суммы")
    public void checkLimitValueForClientPersone() throws FullNameLengthValidationException {
        request = new LoanRequest(12, 10000, LoanType.PERSON, "Sidotav Ivan Ivanovich");
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(ResponseType.APPROVED, response.getType());
    }

    @Test
    @DisplayName("Успешная заявка для клиента с типом Person")
    public void shouldGetApprovedClientPerson() throws FullNameLengthValidationException {
        request = new LoanRequest(5, 8000, LoanType.PERSON, "Sidotav Ivan Ivanovich");
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(ResponseType.APPROVED, response.getType());
    }

    @Test
    @DisplayName("Заявка на неизвестного клиента")
    public void shouldGetExceptionClientUnknown() {
        assertThrows(NullPointerException.class,
                () -> {
                    request = new LoanRequest(5, 8000, null, "Sidotav Ivan Ivanovich");
                    LoanResponse response = sut.createRequest(this.request);
                    assertEquals(ResponseType.APPROVED, response.getType());
                });
    }


    public VeriableLoanCalcRepository fillingArrayCall() throws ApplicatioNotFound, FullNameLengthValidationException {
        request = new LoanRequest(12, 10000, LoanType.PERSON, "Sidotav Ivan Ivanovich");
        LoanResponse response = sut.createRequest(this.request);
        VeriableLoanCalcRepository loanCalcRepository = new VeriableLoanCalcRepository();
        loanCalcRepository.save(request, ResponseType.APPROVED);
            return loanCalcRepository;
    }

    @Test
    @DisplayName("Заявка не найдена")
    public void applicationNotFound() throws FullNameLengthValidationException, ApplicatioNotFound {
        request = new LoanRequest(12, 10000, LoanType.PERSON, "Sidotav Ivan Ivanovich");
        LoanResponse response = sut.createRequest(this.request);
        fillingArrayCall().getResponseUuid(response.getUuid());
        ApplicatioNotFound e = assertThrows(ApplicatioNotFound.class,
                () -> {
                    fillingArrayCall().getResponseUuid(response.getUuid());
                });
        assertEquals("Заявка не найдена", e.getMessage());
    }

    @Test
    @DisplayName("Заявка не найдена при изменение")
    public void unsuccessfulChangeOfApplication() throws FullNameLengthValidationException {
        request = new LoanRequest(12, 10000, LoanType.PERSON, "Sidotav Ivan Ivanovich");
        LoanResponse response = sut.createRequest(this.request);
        ApplicatioNotFound e = assertThrows(ApplicatioNotFound.class,
                () -> {
                    fillingArrayCall().setResponseUuid(response.getUuid(),ResponseType.DENIED);
                });
        assertEquals("Заявка не найдена", e.getMessage());
    }


    @Test
    @DisplayName("Заявка не найдена")
    public void applicationNotFound1() throws FullNameLengthValidationException, ApplicatioNotFound {
        request = new LoanRequest(5, 8000, LoanType.PERSON, "Sidotav Ivan Ivanovich");
        LoanResponse response = sut.createRequest(this.request);

        ResponseType type=fillingArrayCall().getResponseUuid(response.getUuid()).getType();
        assertEquals(type,ResponseType.APPROVED);

    }
}