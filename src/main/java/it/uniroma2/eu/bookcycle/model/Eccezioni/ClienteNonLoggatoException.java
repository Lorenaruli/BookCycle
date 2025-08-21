package it.uniroma2.eu.bookcycle.model.Eccezioni;

public class ClienteNonLoggatoException extends Exception {
    public ClienteNonLoggatoException(String message) {
        super(message);
    }
}
