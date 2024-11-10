package com.jfb.tickets.exceptions;


public class UserUpdateNotAllowedException extends RuntimeException{
    public UserUpdateNotAllowedException(String message) {
        super(message);
    }
}
