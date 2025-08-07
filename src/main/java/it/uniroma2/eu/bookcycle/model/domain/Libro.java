package it.uniroma2.eu.bookcycle.model.domain;

import java.io.Serializable;

import static it.uniroma2.eu.bookcycle.model.domain.StatoLibro.DISPONIBILE;


public class Libro implements Serializable {
    private static long idCounter =1 ;
    private String titolo;
    private String autore;
    private String genere;
    private long idLibro;
    private StatoLibro stato;
    private TipoLibro tipologia;
    private String usernameProprietario;


//    public Libro(String titolo, String autore, String genere, long idLibro, String usernameProprietario, TipoLibro tipologia) {
//        this.titolo = titolo;
//        this.autore = autore;
//        this.genere = genere;
//        this.idLibro = idLibro;
//        this.usernameProprietario=usernameProprietario;
//        this.stato= DISPONIBILE;
//        this.tipologia=tipologia;
//    }
    public Libro(String titolo, String autore, String genere, String usernameProprietario, StatoLibro statoLibro, long idLibro){
        this.titolo = titolo;
        this.autore = autore;
        this.genere = genere;
        this.usernameProprietario=usernameProprietario;
        this.stato=statoLibro;
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

    public void setStato(StatoLibro stato) {
        this.stato = stato;
    }

    public static void setIdCounter(long idCounter) {

        Libro.idCounter = idCounter;
    }

    public StatoLibro getStato() {

        return stato;
    }

    public TipoLibro getTipologia() {

        return tipologia;
    }

    public String getUsernameProprietario() {

        return usernameProprietario;
    }
}


