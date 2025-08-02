package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;

import java.util.List;

public interface PropostaDiScambioDao {

    void salvaRichiesta(PropostaDiScambio proposta) throws DaoException;

    void rimuoviRichiesta(long idProposta) throws DaoException;
    void aggiornaIdCounterDaFile();

    List<PropostaDiScambio> getProposteRicevute(String usernameDestinatario) throws DaoException;

    List<PropostaDiScambio> getProposteInviate(String usernameMittente) throws DaoException;


}
