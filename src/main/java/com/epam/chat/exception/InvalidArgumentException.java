package com.epam.chat.exception;

public class InvalidArgumentException extends RuntimeException{
    public InvalidArgumentException(String s, Throwable e) {
        super(s, e);
    }
}
