package it.uniroma2.eu.bookcycle.model.domain;

public class Libro {
    private String titolo;
    private String autore;
    private String genere;
    private double id;

    public Libro(String titolo, String autore, String genere) {
        this.titolo = titolo;
        this.autore = autore;
        this.genere = genere;
        this.id = 0;

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



    //mancano da aggiungere le vere operazioni che deve fare libro
}


