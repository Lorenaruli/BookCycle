package it.uniroma2.eu.bookcycle.model.dao.memory;

import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.dao.LibroDao;
import it.uniroma2.eu.bookcycle.model.domain.Libro;
import it.uniroma2.eu.bookcycle.model.domain.StatoLibro;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LibroDaoMemory implements LibroDao {
    private List<Libro> libri;
//    private static boolean idCounterInizializzato = false;


    public LibroDaoMemory(){
//        aggiornaIdCounter();
        this.libri=new ArrayList<>();
    }

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

//    public void aggiornaIdCounter(){
//        if (!idCounterInizializzato) {
//            Libro.setIdCounter(0);
//            idCounterInizializzato = true;
//        }
//        long max = 0;
//        for (Libro a : libri) {
//            if (a.getIdLibro() > max) {
//                max = a.getIdLibro();
//            }
//        }
//        Libro.setIdCounter(max + 1);
//    }

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
    public List<Libro> getTuttiLibri() throws DaoException {
        return new ArrayList<>(libri);

    }

//    @Override
//    public List<Libro> getLibri() {
//        return libri.stream()
//                //.filter(l -> l.getStato() == StatoLibro.DISPONIBILE)
//                .collect(Collectors.toList());
//    }
    @Override
    public Libro cercaPerId(long id) {
        return libri.stream()
                .filter(l -> l.getIdLibro() == id)
                .findFirst()
                .orElse(null);
    }



}




