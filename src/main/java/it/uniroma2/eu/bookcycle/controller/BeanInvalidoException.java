package it.uniroma2.eu.bookcycle.controller;

public class BeanInvalidoException extends RuntimeException {
    public BeanInvalidoException(String message) {
        super(message);
    }
}
