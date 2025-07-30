package it.uniroma2.eu.bookcycle.model.domain;

import java.util.ArrayList;
import java.util.List;

public class Utente extends Cliente {
    private List<PropostaDiScambio> propostePendenti;
    public List<Libro> libri;

    // Costruttore
    public Utente(String username,List<PropostaDiScambio> propostePendenti, List<Libro> libri) {
        super(username); // chiama il costruttore della superclasse
        this.propostePendenti = propostePendenti;
        this.libri = new ArrayList<>();
    }
    public void aggiungiLibro (Libro libro ){
        libri.add(libro);
    }

    public void eliminaLibro (Libro libro){
        libri.remove(libro);
    }
    public void aggiungiProposta(PropostaDiScambio proposta) {
        propostePendenti.add(proposta); // deve occuparsene il controller di aggiungere la proposta
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

