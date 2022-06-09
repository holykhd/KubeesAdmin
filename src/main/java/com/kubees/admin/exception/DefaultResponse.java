package com.kubees.admin.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class DefaultResponse<T> {
    private HttpStatus statusCode;
    private String responseMessage;
    private T data;

    public DefaultResponse(HttpStatus statusCode, String responseMessage, T data) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
        this.data = data;
    }

    public DefaultResponse(HttpStatus statusCode, String responseMessage) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
    }

    public DefaultResponse(String responseMessage, T data) {
        this.responseMessage = responseMessage;
        this.data = data;
    }
}
