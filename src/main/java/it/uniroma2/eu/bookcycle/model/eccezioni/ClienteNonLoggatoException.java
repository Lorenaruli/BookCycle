package it.uniroma2.eu.bookcycle.model.eccezioni;

public class ClienteNonLoggatoException extends Exception {
    public ClienteNonLoggatoException(String message) {
        super(message);
    }
}
