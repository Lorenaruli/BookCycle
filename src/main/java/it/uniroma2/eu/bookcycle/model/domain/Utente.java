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

    public Utente(String username, List<PropostaDiScambio> proposteInviate,List<PropostaDiScambio> proposteRicevute, List<Libro> libri) {
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
    public void aggiungiProposteInviate(List<PropostaDiScambio> nuoveProposte) {
        if (proposteInviate == null) {
            proposteInviate = new ArrayList<>();
        }
        proposteInviate.addAll(nuoveProposte);
    }


    public void aggiungiProposteRicevute(List<PropostaDiScambio> nuoveProposte) {
        if (proposteRicevute == null) {
            proposteRicevute = new ArrayList<>();
        }
        proposteRicevute.addAll(nuoveProposte);
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

    public List<Libro> getLibri() {
        return libri;
    }
    public void setLibri(List<Libro> posseduti){
        this.libri=posseduti;
    }
}

