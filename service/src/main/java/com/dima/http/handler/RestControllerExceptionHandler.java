package com.dima.http.handler;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice(basePackages = "com.dima.http.rest")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception, HttpServletRequest request) {
        log.error("Failed to return response", exception);
        return "error/error500";
    }

    @ExceptionHandler(ResponseStatusException.class)
    public String handleException(ResponseStatusException exception, HttpServletRequest request) {
        log.error("Not found", exception);
        return "error/error404";
    }
}
