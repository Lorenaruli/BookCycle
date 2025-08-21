package it.uniroma2.eu.bookcycle.model.dao.memory;

import it.uniroma2.eu.bookcycle.model.Eccezioni.LibroNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.dao.LibroDao;
import it.uniroma2.eu.bookcycle.model.domain.Libro;
import it.uniroma2.eu.bookcycle.model.domain.StatoLibro;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Libro> cercaPerTitolo(String titolo)  {
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
    public List<Libro> cercaPerGenere(String genere){
        return libri.stream()
                .filter(l -> l.getGenere().equalsIgnoreCase(genere))
                .collect(Collectors.toList());

    }
    @Override
    public List<Libro> cercaPerProprietario(String username)  {
        return libri.stream()
                .filter(l -> l.getUsernameProprietario().equalsIgnoreCase(username))
                .collect(Collectors.toList());

    }
    @Override
    public List<Libro> getTuttiLibri()  {
        return new ArrayList<>(libri);

    }


    @Override
    public Libro cercaPerId(long id) throws LibroNonTrovatoException {
        return libri.stream()
                .filter(l -> l.getIdLibro() == id)
                .findFirst()
                .orElseThrow(() ->
                        new LibroNonTrovatoException("Nessun libro trovato con id: " + id));
    }



}




