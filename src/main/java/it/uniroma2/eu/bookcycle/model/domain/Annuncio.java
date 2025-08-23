package it.uniroma2.eu.bookcycle.model.domain;

import java.io.Serializable;


public abstract class Annuncio implements Serializable  {

    private static long idCounter = 1;

    private Libro libro;
    private double prezzo;
    private Libraio libraio;
    private long idAnnuncio;

    protected Annuncio(Libro libro, double prezzo, Libraio libraio) {
        this.libro = libro;
        this.prezzo = prezzo;
        this.libraio= libraio;
        this.idAnnuncio= idCounter++;
    }
    public abstract TipoAnnuncio getTipo();

    public Libro getLibro() {

        return libro;
    }

    public double getPrezzo() {

        return prezzo;
    }

    public Libraio getLibraio() {

        return libraio;
    }

    public long getIdAnnuncio() {

        return idAnnuncio;
    }

    public static void setIdCounter(long idCounter) {
        Annuncio.idCounter = idCounter;
    }
    public long  getIdCounter(long idCounter){

        return idCounter;
    }
}

