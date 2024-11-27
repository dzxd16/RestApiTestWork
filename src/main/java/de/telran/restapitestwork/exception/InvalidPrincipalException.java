package de.telran.restapitestwork.exception;

public class InvalidPrincipalException extends RuntimeException{
    public InvalidPrincipalException(String message) {
        super(message);
    }
}
