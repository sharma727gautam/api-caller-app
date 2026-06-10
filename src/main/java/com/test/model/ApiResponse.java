package com.test.model;

public class ApiResponse {

    private int statusCode;

    private String responseBody;

    public ApiResponse(
            int statusCode,
            String responseBody) {

        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }
}