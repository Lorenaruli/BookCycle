package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PropostaNonTrovataException;
import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;

import java.util.List;

public interface PropostaDiScambioDao {


    void aggiungiProposta(PropostaDiScambio proposta) throws OggettoInvalidoException, PersistenzaException;

    void rimuoviProposta(long idProposta) throws PropostaNonTrovataException, PersistenzaException;
    long aggiornaIdCounter();

    List<PropostaDiScambio> cercaPropostaLibroOfferto(long idLibro);

    List<PropostaDiScambio> cercaPropostaLibroRichiesto(long idLibro);

    PropostaDiScambio cercaPropostaId(long idProposta) throws PropostaNonTrovataException;




}
