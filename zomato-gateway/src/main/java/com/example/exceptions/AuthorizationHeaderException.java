package com.example.exceptions;

public class AuthorizationHeaderException extends RuntimeException{

    public AuthorizationHeaderException(String message) {
        super(message);
    }

}
