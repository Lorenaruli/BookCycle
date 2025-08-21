package it.uniroma2.eu.bookcycle.model.Eccezioni;

public class GuiException extends Exception {
    public GuiException(String message) {
        super(message);
    }
    public GuiException(String message, Throwable cause) {
        super(message, cause);
    }
}