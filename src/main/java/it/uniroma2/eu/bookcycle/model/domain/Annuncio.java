package it.uniroma2.eu.bookcycle.model.domain;

public abstract class Annuncio {
    private Libro libro;
    private double prezzo;
    private Libraio libraio;

    public Annuncio(Libro libro, double prezzo, Libraio libraio) {
        this.libro = libro;
        this.prezzo = prezzo;
        this.libraio= libraio;
    }

    public Libro getLibro() {
        return libro;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public Libraio getLibraio() {
        return libraio;
    }
}

