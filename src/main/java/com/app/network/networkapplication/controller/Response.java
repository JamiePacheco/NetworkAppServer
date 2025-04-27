package com.app.network.networkapplication.controller;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class Response<T>{
    private HttpStatus status;
    private String message;
    private T responseContent;
}