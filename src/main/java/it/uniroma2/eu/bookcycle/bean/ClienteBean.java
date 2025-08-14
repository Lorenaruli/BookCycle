package it.uniroma2.eu.bookcycle.bean;

import it.uniroma2.eu.bookcycle.model.domain.RuoloCliente;

public class ClienteBean {
    private String username;
    RuoloCliente ruoloCliente;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RuoloCliente getRuoloCliente() {
        return ruoloCliente;
    }

    public void setRuoloCliente(RuoloCliente ruoloCliente) {
        this.ruoloCliente = ruoloCliente;
    }
}
