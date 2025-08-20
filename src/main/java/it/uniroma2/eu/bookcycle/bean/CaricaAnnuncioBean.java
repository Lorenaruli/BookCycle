package it.uniroma2.eu.bookcycle.bean;

import it.uniroma2.eu.bookcycle.model.domain.TipoAnnuncio;

public class CaricaAnnuncioBean {
    String titolo;
    String autore;
    double prezzo;
    TipoAnnuncio tipo;

    public CaricaAnnuncioBean(String titolo, String autore, double prezzo, TipoAnnuncio tipo) {
        this.titolo = titolo;
        this.autore = autore;
        this.prezzo = prezzo;
        this.tipo=tipo;
    }

    public CaricaAnnuncioBean(){};

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
}
