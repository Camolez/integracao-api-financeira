package com.br.finnet.integracaoAdiq.controller.handlers;

import com.br.finnet.integracaoAdiq.domain.models.response.MessageErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionsHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<MessageErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getAllErrors()
                .stream()
                .map(error ->
                        MessageErrorResponse
                                .builder()
                                .field(((FieldError) error).getField()) .message(error.getDefaultMessage())
                                .build())
                .collect(Collectors.toList());
    }
}
