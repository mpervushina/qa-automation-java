package com.tinkoff.edu.app;

import java.util.Objects;

public class LoanResponse {
    private final int requestId;
    private final ResponseType type;

    public LoanResponse(int requestId, ResponseType type) {
        this.requestId = requestId;
        this.type = type;
    }

    public int getRequestId() {
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
