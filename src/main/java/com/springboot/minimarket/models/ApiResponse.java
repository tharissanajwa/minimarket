package com.springboot.minimarket.models;

public class ApiResponse {

    private String message;  // Pesan yang akan dikirimkan bersama respons
    private Object data;     // Data yang akan dikirimkan bersama respons

    public ApiResponse() {
        // Constructor default
    }

    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
