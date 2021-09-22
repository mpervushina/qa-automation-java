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
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

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

    @Test
    public void shouldGetErrorWhenApplyNullRequest() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    request = null;
                    LoanResponse response = sut.createRequest(this.request);
                });
    }


    private static Stream<Arguments> dataForApplicationWithЯZeroValues() {
        return Stream.of(
                Arguments.of(12, 0, LoanType.PERSON, "Ivanov Ivan Ivanovich", ResponseType.DENIED),
                Arguments.of(0, 10000, LoanType.PERSON, "Smirnov Egor Petrovich", ResponseType.DENIED)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForApplicationWithЯZeroValues")
    @DisplayName("Проверка на нулевые значения")
    public void shouldGetErrorWhenApplyZero(int months, int amount, LoanType type, String fullName, ResponseType responeType) {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    request = new LoanRequest(months, amount, type, fullName);
                    LoanResponse response = sut.createRequest(this.request);
                    assertEquals(responeType, response);
                });
    }

    private static Stream<Arguments> dataWithUnsuitableFullnameLength() {
        return Stream.of(
                Arguments.of(2, 10000, LoanType.PERSON, "1234567890qwertyuiop555555asdfg"),
                Arguments.of(2, 10000, LoanType.PERSON, "uuu")
        );
    }

    @ParameterizedTest
    @MethodSource("dataWithUnsuitableFullnameLength")
    @DisplayName("Проверки на длину имени")
    public void NameLengthChecks(int months, int amount, LoanType type, String fullName) {
        assertThrows(FullNameLengthValidationException.class,
                () -> {
                    request = new LoanRequest(months, amount, type, fullName);
                    LoanResponse response = sut.createRequest(this.request);
                });
    }

    private static Stream<Arguments> dataForApplication() {
        return Stream.of(
                Arguments.of(12, 10000, LoanType.IP, "Ivanov Ivan Ivanovich", ResponseType.DENIED),
                Arguments.of(10, 9000, LoanType.OOO, "Smirnov Egor Petrovich", ResponseType.DENIED),
                Arguments.of(13, 11000, LoanType.OOO, "Ivanov Ivan Ivanovich", ResponseType.DENIED),
                Arguments.of(12, 10000, LoanType.OOO, "Sidotav Ivan Ivanovich", ResponseType.DENIED),
                Arguments.of(13, 9000, LoanType.PERSON, "Sidotav Ivan Ivanovich", ResponseType.DENIED),
                Arguments.of(12, 13000, LoanType.PERSON, "Sidotav Ivan Ivanovich", ResponseType.DENIED),
                Arguments.of(10, 15000, LoanType.OOO, "Sidotav Ivan Ivanovich", ResponseType.APPROVED),
                Arguments.of(12, 10000, LoanType.PERSON, "Sidotav Ivan Ivanovich", ResponseType.APPROVED),
                Arguments.of(5, 8000, LoanType.PERSON, "Sidotav Ivan Ivanovich", ResponseType.APPROVED)
        );
    }

    @ParameterizedTest
    @MethodSource("dataForApplication")
    @DisplayName("Проверка завки с разными входными параметрами")
    public void shouldGetDisapprovedClientIp(int months, int amount, LoanType type, String fullName, ResponseType responeType) throws FullNameLengthValidationException {
        request = new LoanRequest(months, amount, type, fullName);
        LoanResponse response = sut.createRequest(this.request);
        assertEquals(responeType, response.getType());
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
    @DisplayName("Вывод ид по клиенту ООО")
    public void applicationFromOOO() throws FullNameLengthValidationException {
        request = new LoanRequest(11, 9000, LoanType.OOO, "Sidotav Ivan Ivanovich");
        LoanResponse response = sut.createRequest(this.request);
        VeriableLoanCalcRepository loanCalcRepository = new VeriableLoanCalcRepository();
        loanCalcRepository.save(request, response.getType(), response.getUuid());
        List<UUID> idOOO = loanCalcRepository.getOOO();
        assertEquals(idOOO.get(0), response.getUuid());
    }

    @Test
    @DisplayName("Успешный вывод ответа по ид заяки из файла")
    public void applicationFromUuidFromFile() throws FullNameLengthValidationException, ValueFullException, ApplicatioNotFound {
        request = new LoanRequest(11, 9000, LoanType.OOO, "Sidotav Ivan Ivanovich");
        LoanResponse response = sut.createRequest(this.request);
        LoanCalcRepository fileLoanCalcRepository = new FileLoanCalcRepository();
        fileLoanCalcRepository.save(request, response.getType(), response.getUuid());
        ResponseType type = fileLoanCalcRepository.getResponseUuid(response.getUuid()).getType();
        assertEquals(type, ResponseType.DENIED);
    }

    @Test
    @DisplayName("Заявка не найдна в файле")
    public void applicationNotFoundInFile() {
        LoanCalcRepository fileLoanCalcRepository = new FileLoanCalcRepository();
        ApplicatioNotFound e = assertThrows(ApplicatioNotFound.class,
                () -> {
                    fileLoanCalcRepository.getResponseUuid(UUID.randomUUID());
                });
        assertEquals("Заявка не найдена", e.getMessage());
    }
}