package it.uniroma2.eu.bookcycle.model.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Cliente implements Serializable {
    private String username;
    //private String telefono;
    //private String email;


    // Costruttore
    public Cliente(String username) {
        this.username = username;
    }
    // public abstract void aggiungiLibro(Libro libro, Annuncio annuncio );
    //public abstract void eliminaLibro(Libro libro, Annuncio annuncio);

    public String getUsername() {
        return username;
    }

}