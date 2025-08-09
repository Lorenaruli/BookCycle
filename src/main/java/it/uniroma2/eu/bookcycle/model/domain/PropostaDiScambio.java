package it.uniroma2.eu.bookcycle.model.domain;

import java.io.Serializable;

public class PropostaDiScambio implements Serializable {

    private static long idCounter = 1;

    private Utente mittente;
    private Utente destinatario;
    private Libro libroOfferto;
    private Libro libroRichiesto;
    private StatoProposta stato;
    private long idProposta;

//    public  PropostaDiScambio(Utente mittente, Utente destinatario, Libro libroOfferto, Libro libroRichiesto) {
//        this.mittente = mittente;
//        this.destinatario = destinatario;
//        this.libroOfferto = libroOfferto;
//        this.libroRichiesto = libroRichiesto;
//        this.stato= StatoProposta.IN_ATTESA;
//        //this.idProposta= idCounter++;
//    }

    public  PropostaDiScambio(Utente mittente, Utente destinatario, Libro libroOfferto, Libro libroRichiesto,long idProposta, StatoProposta statoProposta) {
        this.mittente = mittente;
        this.destinatario = destinatario;
        this.libroOfferto = libroOfferto;
        this.libroRichiesto = libroRichiesto;
        this.stato=statoProposta;
        this.idProposta= idProposta;
    }




    public StatoProposta getStato() {
        return this.stato;
    }



    public static long getIdCounter() {
        return idCounter;
    }

    public static void setIdCounter(long idCounter) {
        PropostaDiScambio.idCounter = idCounter;
    }

    public Utente getMittente() {
        return mittente;
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

    public void setStato(StatoProposta stato) {
        this.stato = stato;
    }


    public long getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(long idProposta) {
        this.idProposta = idProposta;
    }
}


