package it.uniroma2.eu.bookcycle.bean;

import it.uniroma2.eu.bookcycle.model.domain.TipoAnnuncio;

public class AnnuncioBean extends CaricaAnnuncioBean{
    long idAnnuncio;

    public AnnuncioBean(String titolo, String autore, double prezzo, TipoAnnuncio tipo, long idAnnuncio) {
        super(titolo, autore, prezzo, tipo);
        this.idAnnuncio = idAnnuncio;
    }


    public long getIdAnnuncio() {
        return idAnnuncio;
    }

    public void setIdAnnuncio(long idAnnuncio) {
        this.idAnnuncio = idAnnuncio;
    }
}
