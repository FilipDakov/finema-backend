package com.fd.finema.bom;

public class Response {

    private ResponseEnum response;
    private String message;
    private Short errorCode;

    public Response(ResponseEnum response, String message, Short errorCode) {
        this.response = response;
        this.message = message;
        this.errorCode = errorCode;
    }

    public Response(String message, Short errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Short getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Short errorCode) {
        this.errorCode = errorCode;
    }

    public ResponseEnum getResponse() {
        return response;
    }

    public void setResponse(ResponseEnum response) {
        this.response = response;
    }
}
