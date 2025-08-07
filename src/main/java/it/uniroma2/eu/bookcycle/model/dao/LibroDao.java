package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.domain.Libro;

import java.util.List;

public interface LibroDao {

    List<Libro> cercaPerTitolo(String titolo) throws DaoException;

    List<Libro> cercaPerAutore(String autore) throws DaoException;

    List<Libro> cercaPerGenere(String genere) throws DaoException;
    List<Libro> cercaPerProprietario(String usernameProprietario) throws DaoException;

    List<Libro> getLibriDisponibili() throws DaoException;
    List<Libro> getTuttiLibri() throws DaoException;

    public void aggiungiLibro(Libro libro);
    public void rimuoviLibro(long idLibro);
    public Libro cercaPerId(long id);

}
