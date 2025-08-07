package it.uniroma2.eu.bookcycle.model.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Cliente implements Serializable {
    private String username;

    public Cliente(String username) {
        this.username = username;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}