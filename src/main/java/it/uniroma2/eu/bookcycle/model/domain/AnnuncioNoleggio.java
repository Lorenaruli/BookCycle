package it.uniroma2.eu.bookcycle.model.domain;

public class AnnuncioNoleggio extends Annuncio{
    private int numMesi;

    public AnnuncioNoleggio(Libro libro, double prezzo, Libraio libraio, int numMesi) {
        super(libro, prezzo, libraio);
        this.numMesi = numMesi;
    }
}
