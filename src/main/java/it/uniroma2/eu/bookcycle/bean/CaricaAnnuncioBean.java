package it.uniroma2.eu.bookcycle.bean;

import it.uniroma2.eu.bookcycle.model.domain.TipoAnnuncio;

public class CaricaAnnuncioBean {
    String titolo;
    String autore;
    double prezzo;
    TipoAnnuncio tipo;
    int durata;

    /**
     * Mi serve costruttore di default i CaricaAnnuncioGui
     */
    public CaricaAnnuncioBean() {
    }

    public CaricaAnnuncioBean(String titolo, String autore, double prezzo, TipoAnnuncio tipo) {
        this.titolo = titolo;
        this.autore = autore;
        this.prezzo = prezzo;
        this.tipo=tipo;
        this.durata=0;
    }

    public CaricaAnnuncioBean(String titolo, String autore, double prezzo, TipoAnnuncio tipo, int durata) {
        this.titolo = titolo;
        this.autore = autore;
        this.prezzo = prezzo;
        this.tipo=tipo;
        this.durata=durata;
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

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public TipoAnnuncio getTipo() {
        return tipo;
    }

    public void setTipo(TipoAnnuncio tipo) {
        this.tipo = tipo;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }
}
