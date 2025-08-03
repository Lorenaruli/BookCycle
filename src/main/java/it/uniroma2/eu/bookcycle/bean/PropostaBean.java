package it.uniroma2.eu.bookcycle.bean;

import it.uniroma2.eu.bookcycle.model.domain.Libro;
import it.uniroma2.eu.bookcycle.model.domain.Utente;

public class PropostaBean {
    private Utente mittente;
    private Utente destinatario;
    private Libro libroOfferto;
    private Libro libroRichiesto;

    public Utente getMittente() {
        return mittente;
    }
    public boolean completo(){
        return mittente != null || destinatario != null|| libroOfferto != null|| libroRichiesto != null;
    }

    public void setMittente(Utente mittente) {
        this.mittente = mittente;
    }

    public Utente getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Utente destinatario) {
        this.destinatario = destinatario;
    }

    public Libro getLibroOfferto() {
        return libroOfferto;
    }

    public void setLibroOfferto(Libro libroOfferto) {
        this.libroOfferto = libroOfferto;
    }

    public Libro getLibroRichiesto() {
        return libroRichiesto;
    }

    public void setLibroRichiesto(Libro libroRichiesto) {
        this.libroRichiesto = libroRichiesto;
    }
}
