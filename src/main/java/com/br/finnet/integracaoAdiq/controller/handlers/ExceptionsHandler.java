package com.br.finnet.integracaoAdiq.controller.handlers;

import com.br.finnet.integracaoAdiq.controller.handlers.responses.ErroResponseModel;
import com.br.finnet.integracaoAdiq.domain.models.response.MessageErrorResponse;
import com.br.finnet.integracaoAdiq.exceptions.FalhaPagamentoAdiqException;
import com.br.finnet.integracaoAdiq.exceptions.PagamentoNaoEncontradoException;
import com.br.finnet.integracaoAdiq.exceptions.TokenInvalidoException;
import com.br.finnet.integracaoAdiq.exceptions.ValorIdPagamentoVazioException;
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

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(value = PagamentoNaoEncontradoException.class)
    public ErroResponseModel pagamentoNaoEncontradoException(PagamentoNaoEncontradoException exception){
        return ErroResponseModel.builder().mensagem(exception.getMessage()).build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = ValorIdPagamentoVazioException.class)
    public ErroResponseModel valorIdPagamentoVazioException(ValorIdPagamentoVazioException exception){
        return ErroResponseModel.builder().mensagem(exception.getMessage()).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = TokenInvalidoException.class)
    public ErroResponseModel tokenInvalidoException(TokenInvalidoException exception){
        return ErroResponseModel.builder().mensagem(exception.getMessage()).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = FalhaPagamentoAdiqException.class)
    public ErroResponseModel falhaPagamentoAdiqException(FalhaPagamentoAdiqException exception){
        return ErroResponseModel.builder().mensagem(exception.getMessage()).build();
    }

}
