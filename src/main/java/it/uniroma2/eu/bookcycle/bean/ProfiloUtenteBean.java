package it.uniroma2.eu.bookcycle.bean;

import it.uniroma2.eu.bookcycle.model.domain.Libro;

import java.util.List;

public class ProfiloUtenteBean {//da eliminare
    String username;
    List<Libro> libriPosseduti;

    public ProfiloUtenteBean() {}

    public List<Libro> getLibriPosseduti() {
        return libriPosseduti;
    }

    public void setLibriPosseduti(List<Libro> libriPosseduti) {
        this.libriPosseduti = libriPosseduti;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}