package it.uniroma2.eu.bookcycle.model.dao.memory;

import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.dao.LibroDao;
import it.uniroma2.eu.bookcycle.model.dao.LibroScambioDao;
import it.uniroma2.eu.bookcycle.model.domain.Libro;
import it.uniroma2.eu.bookcycle.model.domain.StatoLibro;

import java.util.List;
import java.util.stream.Collectors;

public class LibroDaoMemory implements LibroDao {
    private List<Libro> libri;

    @Override
    public void aggiungiLibro(Libro libro) throws DaoException {
        if (libro == null) {
            throw new DaoException("Libro nullo");
        }
        libri.add(libro);
    }
    @Override
    public void rimuoviLibro( long idLibro) throws DaoException{
        boolean removed = libri.removeIf(a -> a.getIdLibro() == idLibro);
        if (!removed) {
            throw new DaoException("Libro non trovato");
        }
    }

    public void aggiornaIdCounter(){
        Libro.setIdCounter(0);;
    }

    @Override
    public List<Libro> cercaPerTitolo(String titolo) throws DaoException {
        return libri.stream()
                .filter(l -> l.getTitolo().equalsIgnoreCase(titolo))
                .collect(Collectors.toList());
    }

    @Override
    public List<Libro> cercaPerAutore(String autore) {
        return libri.stream()
                .filter(l -> l.getAutore().equalsIgnoreCase(autore))
                .collect(Collectors.toList());
    }

    @Override
    public List<Libro> cercaPerGenere(String genere) {
        return libri.stream()
                .filter(l -> l.getGenere().equalsIgnoreCase(genere))
                .collect(Collectors.toList());
    }
    @Override
    public List<Libro> cercaPerProprietario(String username) throws DaoException {
        return libri.stream()
                .filter(l -> l.getUsernameProprietario().equalsIgnoreCase(username))
                .collect(Collectors.toList());
    }

    @Override
    public List<Libro> getLibri() {
        return libri.stream()
                .filter(l -> l.getStato() == StatoLibro.DISPONIBILE)
                .collect(Collectors.toList());
    }
}




