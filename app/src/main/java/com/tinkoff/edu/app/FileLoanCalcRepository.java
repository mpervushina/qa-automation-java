package com.tinkoff.edu.app;


import com.tinkoff.edu.app.enums.ResponseType;
import com.tinkoff.edu.app.exception.ApplicatioNotFound;


import java.io.*;
import java.util.UUID;

public class FileLoanCalcRepository implements LoanCalcRepository {

    @Override
    public LoanResponse save(LoanRequest request, ResponseType type, UUID uuid) {
        try (FileWriter uuidWriter = new FileWriter("FileUUID.txt", true)) {
            String uuidType = String.format("%s - %s\n", uuid, type);
            uuidWriter.write(uuidType);
        }
        catch(Exception e){
        }
        return new LoanResponse(uuid, type);
    }

    @Override
    public LoanResponse getResponseUuid(UUID uuid) throws ApplicatioNotFound {
        ResponseType type = null;
        try (BufferedReader br = new BufferedReader(new FileReader("FileUUID.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(uuid.toString())) {
                    type = ResponseType.valueOf(line.substring(uuid.toString().length() + 3));
                }
            }
            if (type == null) {
                throw new ApplicatioNotFound("Заявка не найдена");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new LoanResponse(uuid, type);
    }
}
