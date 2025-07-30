package it.uniroma2.eu.bookcycle.model.domain;

public class PropostaDiScambio {
    private Utente mittente;
    private Utente destinatario;
    private Libro libroOfferto;
    private Libro libroRichiesto;
    private String stato;

    public void PropostaDiScambioropostaDiScambio(Utente mittente, Utente destinatario, Libro libroOfferto, Libro libroRichiesto) {
        this.mittente = mittente;
        this.destinatario = destinatario;
        this.libroOfferto = libroOfferto;
        this.libroRichiesto = libroRichiesto;
        this.stato= "in attesa";
    }


    public String getStato() {
        System.out.println("La proposta è " + this.stato);
        return this.stato;
        //qualcuno dovra occuparsi di aggiornare lo stato di proposta quando viene accettata o rifiutata

    }

    public Utente getMittente() {
        return mittente;
    }

    public Utente getDestinatario() {
        return destinatario;
    }
}


