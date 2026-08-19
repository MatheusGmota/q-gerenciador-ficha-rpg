package br.com.api.domain.exceptions;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) { super(message); }
}