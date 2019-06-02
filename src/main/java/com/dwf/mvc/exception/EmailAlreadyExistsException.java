package com.dwf.mvc.exception;

public class EmailAlreadyExistsException extends Exception {

    public EmailAlreadyExistsException(String message){
        super(message);
    }
}
