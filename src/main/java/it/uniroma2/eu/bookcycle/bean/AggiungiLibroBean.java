package it.uniroma2.eu.bookcycle.bean;

import it.uniroma2.eu.bookcycle.model.domain.RuoloCliente;

public class AggiungiLibroBean {
    private String titolo;
    private String autore;
    private String genere;

    public AggiungiLibroBean() {
    }

    public boolean completo(){
        return titolo != null || autore != null || genere != null;
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

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
}
