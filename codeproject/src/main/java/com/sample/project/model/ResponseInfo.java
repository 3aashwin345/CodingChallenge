package com.sample.project.model;

public class ResponseInfo {

    private String statusMessage;

    private int statusCode;
    
    public ResponseInfo(int code, String message){
        this.statusMessage = message;
        this.statusCode = code;
    }

    public void setMessage(String message) {
        this.statusMessage = message;
    }

    public void setMessageCode(int messageCode) {
        this.statusCode = messageCode;
    }

    public String getMessage() {
        return statusMessage;
    }

    public int getMessageCode() {
        return statusCode;
    }
    
    
}
