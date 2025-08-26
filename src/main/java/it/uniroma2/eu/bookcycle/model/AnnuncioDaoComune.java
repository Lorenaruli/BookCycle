package it.uniroma2.eu.bookcycle.model;

import it.uniroma2.eu.bookcycle.model.domain.Annuncio;
import it.uniroma2.eu.bookcycle.model.domain.TipoAnnuncio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnnuncioDaoComune {
    private List<Annuncio> annunci;

    public void aggiornaIdCounter() {
        long max = 0;
        for (Annuncio a : annunci) {
            if (a.getIdAnnuncio() > max) {
                max = a.getIdAnnuncio();
            }
        }
        Annuncio.setIdCounter(max + 1);
    }

    public List<Annuncio> ottieniTuttiAnnunci() {
        return new ArrayList<>(annunci);
    }

    public List<Annuncio> cercaPerTitolo(String titolo) {
        if (titolo == null) {
            return Collections.emptyList();
        }
        List<Annuncio> risultati = new ArrayList<>();
        for (Annuncio a : annunci) {
            if (a.getLibro().getTitolo().toLowerCase().contains(titolo.toLowerCase())) {
                risultati.add(a);
            }
        }
        return risultati;
    }

    public List<Annuncio> cercaPerAutore(String autore) {
        if (autore == null) {
            return Collections.emptyList();
        }
        List<Annuncio> risultati = new ArrayList<>();
        for (Annuncio a : annunci) {
            if (a.getLibro().getAutore().toLowerCase().contains(autore.toLowerCase())) {
                risultati.add(a);
            }
        }
        return risultati;
    }


    public List<Annuncio> cercaPerGenere(String genere) {
        if (genere == null) {
            return Collections.emptyList();
        }
        List<Annuncio> risultati = new ArrayList<>();
        for (Annuncio a : annunci) {
            if (a.getLibro().getGenere().toLowerCase().contains(genere.toLowerCase())) {
                risultati.add(a);
            }
        }
        return risultati;
    }


    public List<Annuncio> ottieniAnnunciPerTipo(TipoAnnuncio tipo) {
        if (tipo == null) {
            return Collections.emptyList();
        }
        List<Annuncio> risultati = new ArrayList<>();
        for (Annuncio a : annunci) {
            if (a.getTipo() == tipo) {
                risultati.add(a);
            }
        }
        return risultati;
    }


}
