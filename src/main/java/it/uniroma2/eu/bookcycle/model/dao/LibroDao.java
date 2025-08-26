package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.eccezioni.LibroNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.domain.Libro;

import java.util.List;

public interface LibroDao {

    List<Libro> cercaPerTitolo(String titolo);

    List<Libro> cercaPerAutore(String autore) ;

    List<Libro> cercaPerGenere(String genere);

    List<Libro> getTuttiLibri();

     void aggiungiLibro(Libro libro) throws PersistenzaException, OggettoInvalidoException;
     void rimuoviLibro(long idLibro) throws PersistenzaException, LibroNonTrovatoException;
    Libro cercaPerId(long id) throws LibroNonTrovatoException;

}
