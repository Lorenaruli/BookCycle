package it.uniroma2.eu.bookcycle.model.dao.memory;

import it.uniroma2.eu.bookcycle.model.eccezioni.LibroNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.dao.LibroDao;
import it.uniroma2.eu.bookcycle.model.domain.Libro;

import java.util.ArrayList;
import java.util.List;

public class LibroDaoMemory implements LibroDao {
    private List<Libro> libri;


    public LibroDaoMemory(){
        this.libri=new ArrayList<>();
    }

    @Override
    public void aggiungiLibro(Libro libro) throws OggettoInvalidoException {
        if (libro == null) {
            throw new OggettoInvalidoException("Libro nullo");
        }
        libri.add(libro);
    }
    @Override
    public void rimuoviLibro( long idLibro) throws LibroNonTrovatoException {
        boolean removed = libri.removeIf(a -> a.getIdLibro() == idLibro);
        if (!removed) {
            throw new LibroNonTrovatoException("Libro non trovato");
        }
    }


    @Override
    public List<Libro> cercaPerTitolo(String titolo) {
        List<Libro> risultati = new ArrayList<>();
        for (Libro l : libri) {
            if (l.getTitolo().equalsIgnoreCase(titolo)) {
                risultati.add(l);
            }
        }
        return risultati;
    }

    @Override
    public List<Libro> cercaPerAutore(String autore) {
        List<Libro> risultati = new ArrayList<>();
        for (Libro l : libri) {
            if (l.getAutore().equalsIgnoreCase(autore)) {
                risultati.add(l);
            }
        }
        return risultati;
    }

    @Override
    public List<Libro> cercaPerGenere(String genere) {
        List<Libro> risultati = new ArrayList<>();
        for (Libro l : libri) {
            if (l.getGenere().equalsIgnoreCase(genere)) {
                risultati.add(l);
            }
        }
        return risultati;
    }

    @Override
    public List<Libro> getTuttiLibri()  {
        return new ArrayList<>(libri);

    }


    @Override
    public Libro cercaPerId(long id) throws LibroNonTrovatoException {
        for (Libro l : libri) {
            if (l.getIdLibro() == id) {
                return l;
            }
        }
        throw new LibroNonTrovatoException("Nessun libro trovato con id: " + id);
    }




}




