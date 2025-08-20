package it.uniroma2.eu.bookcycle.model.domain;

import java.util.ArrayList;
import java.util.List;

public class Utente extends Cliente {
    private List<PropostaDiScambio> proposteInviate = new ArrayList<>();
    private List<PropostaDiScambio> proposteRicevute = new ArrayList<>();
    private List<Libro> libri;



    public Utente(String username) {
        super(username);
        proposteInviate = new ArrayList<>();
        proposteRicevute= new ArrayList<>();
        libri=new ArrayList<>();
    }

    public Utente(String username, List<PropostaDiScambio> proposteInviate,List<PropostaDiScambio> proposteRicevute, List<Libro> libri) {
        super(username);
        this.proposteInviate = proposteInviate;
        this.proposteRicevute= proposteRicevute;
        this.libri = new ArrayList<>();
    }


    public void aggiungiLibro (Libro libro ){
        libri.add(libro);
    }

    public void eliminaLibro (long idLibro){
             libri.removeIf(libro -> libro.getIdLibro() == idLibro);
    }
    public void aggiungiPropostaInviata(PropostaDiScambio proposta) {
        if (proposteInviate == null) {
            proposteInviate = new ArrayList<>();
        }
        proposteInviate.add(proposta);
    }


    public void aggiungiPropostaRicevuta(PropostaDiScambio proposta) {
        if (proposteRicevute == null) {
            proposteRicevute = new ArrayList<>();
        }
        proposteRicevute.add(proposta);
    }

    public void rimuoviPropostaInviata(long idProposta) {
        proposteInviate.removeIf(propostaDiScambio -> propostaDiScambio.getIdProposta() == idProposta);
    }

    public void rimuoviPropostaRicevuta(long idProposta) {
        proposteRicevute.removeIf(propostaDiScambio -> propostaDiScambio.getIdProposta()==idProposta);
    }

    public List<PropostaDiScambio> getProposteInviate() {
        return  (proposteInviate);
    }

    public List<PropostaDiScambio> getProposteRicevute() {
        return (proposteRicevute);
    }

    public void vediListaLibri() {
        if (libri.isEmpty()) {
            System.out.println("Nessun libro disponibile.");
        } else {
            for (Libro libro : libri) {
                System.out.println(" " + libro.getTitolo() + " di " + libro.getAutore());
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

