package com.github.dwflibrary.mvc.exception;

public class IdViolationException extends Exception {

    public IdViolationException(String message, Object valueExpected, Object valuePassed){
        super(message+". Value expected: "+valueExpected+". Value passed: "+valuePassed);
    }

}
