package it.uniroma2.eu.bookcycle.bean;

import it.uniroma2.eu.bookcycle.model.domain.StatoLibro;

public class LibroBean {
    String titolo;
    String autore;
    String genere;
    long id;
    String usernameProprietario;
//    StatoLibro stato;

    public LibroBean(String titolo, String autore, String genere, long id, String username) {
        this.titolo = titolo;
        this.autore = autore;
        this.genere = genere;
        this.id = id;
        this.usernameProprietario=username;
//        this.stato=stato;

    }

    public LibroBean() {
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public long getIdLibro() {
        return id;
    }

    public void setIdLibro(long id) {
        this.id = id;
    }

    public String getUsernameProprietario() {
        return usernameProprietario;
    }

    public void setUsernameProprietario(String usernameProprietario) {
        this.usernameProprietario = usernameProprietario;
    }

//    public StatoLibro getStato() {
//        return stato;
//    }
//
//    public void setStato(StatoLibro stato) {
//        this.stato = stato;
//    }
}
