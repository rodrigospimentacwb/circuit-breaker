package com.pepper.model;

public class RestTemplateJson {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RestTemplateJson{" +
                "message='" + message + '\'' +
                '}';
    }
}
