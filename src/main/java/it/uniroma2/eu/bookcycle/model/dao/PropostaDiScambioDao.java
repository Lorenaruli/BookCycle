package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;

import java.util.List;

public interface PropostaDiScambioDao {

    void salvaRichiesta(PropostaDiScambio proposta) throws DaoException;

    void rimuoviRichiesta(long idProposta) throws DaoException;
    void aggiornaIdCounterDaFile();

    List<it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio> getProposteRicevute(String usernameDestinatario) throws DaoException;

    List<it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio> getProposteInviate(String usernameMittente) throws DaoException;


}
