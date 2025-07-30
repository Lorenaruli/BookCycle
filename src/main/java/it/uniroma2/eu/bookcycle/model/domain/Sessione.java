package it.uniroma2.eu.bookcycle.model.domain;

public class Sessione {
    private Cliente clienteLoggato;
    private static Sessione istanza;

    public static Sessione ottieniIstanza(){
        if (istanza == null){
            istanza = new Sessione();
        }
        return istanza;
    }

    public Cliente getClienteLoggato() {
        return clienteLoggato;
    }

    public void setClienteLoggato(Cliente clienteLoggato) {
        this.clienteLoggato = clienteLoggato;
    }
}
