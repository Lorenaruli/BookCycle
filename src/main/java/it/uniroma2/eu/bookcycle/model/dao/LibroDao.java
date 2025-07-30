package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.domain.Libro;

import java.util.List;

public interface LibroDao {

    List<Libro> cercaPerTitolo(String titolo) throws DaoException;

    List<Libro> cercaPerAutore(String autore) throws DaoException;

    List<Libro> cercaPerGenere(String genere) throws DaoException;

    List<Libro> getTuttiLibriScambiabili() throws DaoException;
}
