package com.tinkoff.edu.app;

public class LoanResponse {
    private final int requestId;
    private final LoanRequest request;
    private final ResponseType type;

    public LoanResponse(int requestId, LoanRequest request, ResponseType type) {
        this.requestId = requestId;
        this.request = request;
        this.type = type;
    }

    public int getRequestId() {
        return requestId;
    }

    public LoanRequest getRequest() {
        return request;
    }

    public ResponseType getType() {
        return type;
    }
}
