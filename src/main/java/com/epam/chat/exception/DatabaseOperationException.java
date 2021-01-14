package com.epam.chat.exception;

public class DatabaseOperationException extends RuntimeException{
    public DatabaseOperationException(String s, Throwable e) {
        super(s, e);
    }
}
