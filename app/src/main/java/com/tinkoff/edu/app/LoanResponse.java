package com.tinkoff.edu.app;

import java.util.Objects;
import java.util.UUID;

public class LoanResponse {
    private final String requestId;
    private final ResponseType type;

    public LoanResponse( ResponseType type) {
        this.requestId= UUID.randomUUID().toString();
        this.type = type;
    }

    public String getRequestId() {
        return requestId;
    }

    public ResponseType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanResponse response = (LoanResponse) o;
        return requestId == response.requestId &&
                type == response.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, type);
    }
}
