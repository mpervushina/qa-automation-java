package com.tinkoff.edu.app;

import com.tinkoff.edu.app.enums.ResponseType;

import java.util.Objects;
import java.util.UUID;

public class LoanResponse {
    private final UUID uuid;
    private ResponseType type;

    public LoanResponse(UUID uuid, ResponseType type) {
        this.uuid= uuid;
        this.type = type;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ResponseType getType() {
        return type;
    }
    public void setType(ResponseType type) {
        this.type = type;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanResponse response = (LoanResponse) o;
        return uuid.equals(response.uuid)  &&
                type == response.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, type);
    }

}
