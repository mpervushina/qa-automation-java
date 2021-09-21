package com.tinkoff.edu;


import com.tinkoff.edu.app.*;
import com.tinkoff.edu.app.enums.LoanType;
import com.tinkoff.edu.app.enums.ResponseType;
import com.tinkoff.edu.app.exception.ApplicatioNotFound;
import com.tinkoff.edu.app.exception.FullNameLengthValidationException;
import com.tinkoff.edu.app.exception.ValueFullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;


import java.util.List;
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
        request = new LoanRequest(12, 10000, LoanType.PERSON, "Sidotav Ivan Ivanovich");
        VeriableLoanCalcRepository loanCalcRepository = new VeriableLoanCalcRepository();
        sut = new LoanCalcController(new StaticVeriableLoanCalcService(loanCalcRepository));

    }

    @ParameterizedTest
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
    @DisplayName("Полное имя по длине больше разрешенного")
    public void FullNameMoreLength() {
        assertThrows(FullNameLengthValidationException.class,
                () -> {
                    request = new LoanRequest(2, 10000, LoanType.PERSON, "1234567890qwertyuiop555555asdfg");
                    LoanResponse response = sut.createRequest(this.request);
                });
    }

    @Test
    @DisplayName("Полное имя по длине меньше разрешенного")
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

    public VeriableLoanCalcRepository fillingArrayCall() throws FullNameLengthValidationException {
        LoanResponse response = sut.createRequest(this.request);
        VeriableLoanCalcRepository loanCalcRepository = new VeriableLoanCalcRepository();
        loanCalcRepository.save(request, response.getType(), response.getUuid());
        return loanCalcRepository;
    }

    @Test
    @DisplayName("Заявка не найдена")
    public void applicationNotFound() {
        ApplicatioNotFound e = assertThrows(ApplicatioNotFound.class,
                () -> {
                    fillingArrayCall().getResponseUuid(UUID.randomUUID());
                });
        assertEquals("Заявка не найдена", e.getMessage());
    }

    @Test
    @DisplayName("При изменение ответа , заявка не найдена")
    public void applicationNotFoundChanges() {
        ApplicatioNotFound e = assertThrows(ApplicatioNotFound.class,
                () -> {
                    fillingArrayCall().setResponseUuid(UUID.randomUUID(), ResponseType.DENIED);
                });
        assertEquals("Заявка не найдена", e.getMessage());
    }

    @Test
    @DisplayName("Проверка вывода ответа по ид заявке")
    public void checkGetResponseType() throws FullNameLengthValidationException, ApplicatioNotFound {
        request = new LoanRequest(12, 10000, LoanType.PERSON, "Sidotav Ivan Ivanovich");
        LoanResponse response = sut.createRequest(this.request);
        VeriableLoanCalcRepository loanCalcRepository = new VeriableLoanCalcRepository();
        loanCalcRepository.save(request, response.getType(), response.getUuid());
        ResponseType type = loanCalcRepository.getResponseUuid(response.getUuid()).getType();
        assertEquals(ResponseType.APPROVED, type);
    }

    @Test
    @DisplayName("Проверка изменения ответа по ид заявке")
    public void changeCheckResponseType() throws FullNameLengthValidationException, ApplicatioNotFound {
        request = new LoanRequest(12, 10000, LoanType.PERSON, "Sidotav Ivan Ivanovich");
        LoanResponse response = sut.createRequest(this.request);
        VeriableLoanCalcRepository loanCalcRepository = new VeriableLoanCalcRepository();
        loanCalcRepository.save(request, response.getType(), response.getUuid());
        ResponseType type = loanCalcRepository.setResponseUuid(response.getUuid(), ResponseType.DENIED).getType();
        assertEquals(ResponseType.DENIED, type);
    }

    @Test
    @DisplayName("Вывод ид поклиенту ООО")
    public void applicationFromOOO() throws FullNameLengthValidationException {
        request = new LoanRequest(11, 9000, LoanType.OOO, "Sidotav Ivan Ivanovich");
        LoanResponse response = sut.createRequest(this.request);
        VeriableLoanCalcRepository loanCalcRepository = new VeriableLoanCalcRepository();
        loanCalcRepository.save(request, response.getType(), response.getUuid());
        List<UUID> idOOO = loanCalcRepository.getOOO();
        assertEquals(idOOO.get(0), response.getUuid());
    }

    @Test
    public void applicationFrom1OOO() throws FullNameLengthValidationException, ValueFullException, ApplicatioNotFound {
        request = new LoanRequest(11, 9000, LoanType.OOO, "Sidotav Ivan Ivanovich");
        LoanResponse response = sut.createRequest(this.request);
        LoanCalcRepository fileLoanCalcRepository = new FileLoanCalcRepository();
        fileLoanCalcRepository.save(request, response.getType(), response.getUuid());
        LoanResponse type = fileLoanCalcRepository.getResponseUuid(response.getUuid());
        System.out.println(type);
    }

}