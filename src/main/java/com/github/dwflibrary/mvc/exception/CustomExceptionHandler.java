package com.github.dwflibrary.mvc.exception;

import com.github.dwflibrary.util.Util;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String messageUser      = "Mensagem inválida.";
        String messageDeveloper = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        return handleExceptionInternal(ex, Arrays.asList(create(messageUser, messageDeveloper, ex)), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, createErrorList(ex), headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request){
        String messageUser      = "Recurso não encontrado;";
        String messageDeveloper = ExceptionUtils.getRootCauseMessage(ex);
        return handleExceptionInternal(ex, Arrays.asList(create(messageUser, messageDeveloper, ex)), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({IdViolationException.class})
    public ResponseEntity handleIdViolationException(IdViolationException ex, WebRequest request){
        String messageUser      = "Id do objeto passado não é o mesmo id passado na URL da requisição;";
        String messageDeveloper = ExceptionUtils.getRootCauseMessage(ex);
        return handleExceptionInternal(ex, Arrays.asList(create(messageUser, messageDeveloper, ex)), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request){
        String messageUser      = "Operação não permitida por violar restrição de integridade. Existem registros dependentes dele.";
        String messageDeveloper = ExceptionUtils.getRootCauseMessage(ex);
        return handleExceptionInternal(ex, Arrays.asList(create(messageUser, messageDeveloper, ex)), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({EmailAlreadyExistsException.class})
    public ResponseEntity handleDataIntegrityViolationException(EmailAlreadyExistsException ex, WebRequest request){
        String messageUser      = ex.getMessage();
        String messageDeveloper = ExceptionUtils.getRootCauseMessage(ex);
        return handleExceptionInternal(ex, Arrays.asList(create(messageUser, messageDeveloper, ex)), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    private List<Error> createErrorList(MethodArgumentNotValidException ex){
        List<Error> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach( fieldError -> {
            String messageUser      = "Valor informado no campo '"+fieldError.getField()+"' não é permitido. Valor Rejeitado: "+fieldError.getRejectedValue();
            String messageDeveloper = fieldError.toString();
            errors.add(create(messageUser, messageDeveloper, null));
        });
        return errors;
    }

    private Error create(String messageUser, String messageDeveloper, Throwable throwable){
        return Error.builder().messageUser(messageUser).messageToDeveloper(messageDeveloper).stackTrace(Util.stackTraceToString(throwable)).build();
    }
}
