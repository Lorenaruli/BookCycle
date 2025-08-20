package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.domain.Annuncio;
import it.uniroma2.eu.bookcycle.model.domain.TipoAnnuncio;

import java.util.List;

public interface AnnuncioDao {

     void aggiornaIdCounter();
    void salvaAnnuncio(Annuncio annuncio) throws DaoException;

    void rimuoviAnnuncio(long idAnnuncio) throws DaoException;
     List<Annuncio> cercaPerProprietario(String username) throws DaoException;


    List<Annuncio> ottieniTuttiAnnunci() throws DaoException;


    List<Annuncio> ottieniAnnunciPerLibraio(String usernameLibraio) throws DaoException;


    List<Annuncio> cercaPerTitolo(String titolo) throws DaoException;


    List<Annuncio> cercaPerAutore(String autore) throws DaoException;


    List<Annuncio> cercaPerGenere(String genere) throws DaoException;


    List<Annuncio> ottieniAnnunciPerTipo(TipoAnnuncio tipo) throws DaoException;
}
