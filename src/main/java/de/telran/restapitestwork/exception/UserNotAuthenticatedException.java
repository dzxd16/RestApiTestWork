package de.telran.restapitestwork.exception;

public class UserNotAuthenticatedException extends RuntimeException{
    public UserNotAuthenticatedException(String message) {
        super(message);
    }
}
