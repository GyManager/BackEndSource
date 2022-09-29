package org.gymanager.configuration.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.HashMap;

@ControllerAdvice
@Slf4j
public class GyManagerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { DataIntegrityViolationException.class })
    protected ResponseEntity<Object> handleDataIntegrityViolation(RuntimeException ex, WebRequest request) {
        var headers = new HttpHeaders();
        var status = HttpStatus.CONFLICT;
        var message = "La operacion que usted esta intentando hacer para este registro no esta permitida actualmente,"
                .concat(" porque el registro esta en uso. Contacte al administrador del sistema");
        var error = status.getReasonPhrase();
        var httpStatus = status.value();
        var timestamp = Instant.now();
        var path = ((ServletWebRequest)request).getRequest().getRequestURI();

        var body = new HashMap<>();
        body.put("message", message);
        body.put("error", error);
        body.put("status", httpStatus);
        body.put("timestamp", timestamp);
        body.put("path", path);

        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        var errors = ex.getBindingResult().getAllErrors();
        var message = "Validation failed for object='"
                .concat(ex.getBindingResult().getObjectName())
                .concat("'. Error count: ")
                .concat(String.valueOf(errors.size()));
        var error = status.getReasonPhrase();
        var httpStatus = status.value();
        var timestamp = Instant.now();
        var path = ((ServletWebRequest)request).getRequest().getRequestURI();

        var body = new HashMap<>();
        body.put("errors", errors);
        body.put("message", message);
        body.put("error", error);
        body.put("status", httpStatus);
        body.put("timestamp", timestamp);
        body.put("path", path);

        return handleExceptionInternal(ex, body, headers, status, request);
    }
}
