package com.tinkoff.edu.app;

public class LoanResponse {
    private final int requestId;
    private final LoanRequest request;

    public LoanResponse(int requestId,LoanRequest request){
        this.requestId=requestId;
        this.request=request;
    }

    public int getRequestId() {
        return requestId;
    }

    public LoanRequest getRequest() {
        return request;
    }
}
