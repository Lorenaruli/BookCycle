package it.uniroma2.eu.bookcycle.model.domain;

import java.io.Serializable;

import static it.uniroma2.eu.bookcycle.model.domain.StatoLibro.DISPONIBILE;


public class Libro implements Serializable {
    private static long idCounter =1 ;
    private String titolo;
    private String autore;
    private String genere;
    private long idLibro;
    private String usernameProprietario;


    public Libro(String titolo, String autore, String genere, String usernameProprietario){
        this.titolo = titolo;
        this.autore = autore;
        this.genere = genere;
        this.usernameProprietario=usernameProprietario;

    }

    public Libro(String titolo, String autore, String usernameProprietario, long id){
        this.titolo = titolo;
        this.autore = autore;
        this.usernameProprietario=usernameProprietario;
        this.idLibro=id;

    }

    public Libro(String titolo, String autore, String genere, String usernameProprietario, long idLibro){
        this.titolo = titolo;
        this.autore = autore;
        this.genere = genere;
        this.usernameProprietario=usernameProprietario;
        this.idLibro= idLibro;

    }

    public String getTitolo() {

        return titolo;
    }


    public String getAutore() {

        return autore;
    }


    public String getGenere() {

        return genere;
    }

    public long getIdLibro() {

        return idLibro;
    }

    public static long getIdCounter() {

        return idCounter;
    }


    public static void setIdCounter(long idCounter) {

        Libro.idCounter = idCounter;
    }


    public String getUsernameProprietario() {

        return usernameProprietario;
    }
}


