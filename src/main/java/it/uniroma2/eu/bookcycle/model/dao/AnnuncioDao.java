package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.domain.Annuncio;
import it.uniroma2.eu.bookcycle.model.domain.TipoAnnuncio;

import java.util.List;

public interface AnnuncioDao {


    void salvaAnnuncio(Annuncio annuncio) throws DaoException;

    void rimuoviAnnuncio(Annuncio annuncio) throws DaoException;


    List<Annuncio> getTuttiAnnunci() throws DaoException;


    List<Annuncio> getAnnunciPerLibraio(String usernameLibraio) throws DaoException;


    List<Annuncio> cercaPerTitolo(String titolo) throws DaoException;


    List<Annuncio> cercaPerAutore(String autore) throws DaoException;


    List<Annuncio> cercaPerGenere(String genere) throws DaoException;


    List<Annuncio> getAnnunciPerTipo(TipoAnnuncio tipo) throws DaoException;
}
