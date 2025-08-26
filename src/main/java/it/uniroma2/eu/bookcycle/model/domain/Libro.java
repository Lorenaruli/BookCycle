package it.uniroma2.eu.bookcycle.model.domain;

import java.io.Serializable;



public class Libro implements Serializable {
    private static long idCounter =1 ;
    private String titolo;
    private String autore;
    private String genere;
    private long idLibro;


    public Libro(String titolo, String autore, String genere){
        this.titolo = titolo;
        this.autore = autore;
        this.genere = genere;

    }

    public Libro(String titolo, String autore, long id){
        this.titolo = titolo;
        this.autore = autore;
        this.idLibro=id;

    }

    public Libro(String titolo, String autore, String genere, long idLibro){
        this.titolo = titolo;
        this.autore = autore;
        this.genere = genere;
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


}


