package com.epam.chat.exception;


public class NullValueResourceException extends RuntimeException {

    public NullValueResourceException(String s, Throwable e) {
        super(s, e);
    }
}
