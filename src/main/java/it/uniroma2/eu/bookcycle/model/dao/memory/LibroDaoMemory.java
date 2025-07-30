package it.uniroma2.eu.bookcycle.model.dao.memory;

import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.dao.LibroDao;
import it.uniroma2.eu.bookcycle.model.domain.Libro;
import it.uniroma2.eu.bookcycle.model.domain.Utente;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LibroDaoMemory implements LibroDao {

    private final List<Utente> utenti;

    public LibroDaoMemory(List<Utente> utenti) {
        this.utenti = utenti;
    }

    @Override
    public List<Libro> cercaPerTitolo(String titolo) throws DaoException {
        return utenti.stream()
                .flatMap(u -> u.getLibri().stream())
                .filter(l -> l.getTitolo().equalsIgnoreCase(titolo))
                .collect(Collectors.toList());
    }

    @Override
    public List<Libro> cercaPerAutore(String autore) throws DaoException {
        return utenti.stream()
                .flatMap(u -> u.getLibri().stream())
                .filter(l -> l.getAutore().equalsIgnoreCase(autore))
                .collect(Collectors.toList());
    }

    @Override
    public List<Libro> cercaPerGenere(String genere) throws DaoException {
        return utenti.stream()
                .flatMap(u -> u.getLibri().stream())
                .filter(l -> l.getGenere().equalsIgnoreCase(genere))
                .collect(Collectors.toList());
    }

    @Override
    public List<Libro> getTuttiLibriScambiabili() throws DaoException {
        return utenti.stream()
                .flatMap(u -> u.getLibri().stream())
                .collect(Collectors.toList());
    }
}

