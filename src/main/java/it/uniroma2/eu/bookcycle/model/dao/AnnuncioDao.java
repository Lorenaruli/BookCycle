package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.domain.Annuncio;
import it.uniroma2.eu.bookcycle.model.domain.TipoAnnuncio;

import java.util.List;

public interface AnnuncioDao {

    void aggiornaIdCounter();
    void salvaAnnuncio(Annuncio annuncio) throws OggettoInvalidoException, PersistenzaException;

    void rimuoviAnnuncio(long idAnnuncio) throws OggettoInvalidoException, PersistenzaException;
     List<Annuncio> cercaPerProprietario(String username) ;


    List<Annuncio> ottieniTuttiAnnunci() ;


    List<Annuncio> ottieniAnnunciPerLibraio(String usernameLibraio);


    List<Annuncio> cercaPerTitolo(String titolo);


    List<Annuncio> cercaPerAutore(String autore);


    List<Annuncio> cercaPerGenere(String genere);


    List<Annuncio> ottieniAnnunciPerTipo(TipoAnnuncio tipo);
}
