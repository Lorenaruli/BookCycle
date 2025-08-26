package it.uniroma2.eu.bookcycle.model.eccezioni;

public class PersistenzaException extends RuntimeException {
    public PersistenzaException(String message) {
        super(message);
    }
}
