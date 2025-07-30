package it.uniroma2.eu.bookcycle.model.domain;

import java.util.ArrayList;
import java.util.List;

public class Utente extends Cliente {
    private List<PropostaDiScambio> proposteInviate = new ArrayList<>();
    private List<PropostaDiScambio> proposteRicevute = new ArrayList<>();
    public List<Libro> libri;

    // Costruttore


    public Utente(String username) {
        super(username);
    }

    public Utente(String username, List<PropostaDiScambio> propostePendenti, List<Libro> libri) {
        super(username); // chiama il costruttore della superclasse
        this.proposteInviate = proposteInviate;
        this.proposteRicevute= proposteRicevute;
        this.libri = new ArrayList<>();
    }
    public void aggiungiLibro (Libro libro ){
        libri.add(libro);
    }

    public void eliminaLibro (Libro libro){
        libri.remove(libro);
    }
    public void aggiungiPropostaInviata(PropostaDiScambio proposta) {
        proposteInviate.add(proposta);
    }

    public void aggiungiPropostaRicevuta(PropostaDiScambio proposta) {
        proposteRicevute.add(proposta);
    }

    public void rimuoviPropostaInviata(PropostaDiScambio proposta) {
        proposteInviate.remove(proposta);
    }

    public void rimuoviPropostaRicevuta(PropostaDiScambio proposta) {
        proposteRicevute.remove(proposta);
    }

    public List<PropostaDiScambio> getProposteInviate() {
        return new ArrayList<>(proposteInviate);
    }

    public List<PropostaDiScambio> getProposteRicevute() {
        return new ArrayList<>(proposteRicevute);
    }

    public void vediListaLibri() {
        if (libri.isEmpty()) {
            System.out.println("Nessun libro disponibile.");
        } else {
            for (Libro libro : libri) {
                System.out.println("- " + libro.getTitolo() + " di " + libro.getAutore());
            }
        }
    }

}

