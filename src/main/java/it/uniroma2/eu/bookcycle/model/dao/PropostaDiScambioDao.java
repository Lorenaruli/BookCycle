package it.uniroma2.eu.bookcycle.model.dao;

import java.util.List;

public interface PropostaDiScambioDao {

    void salvaRichiesta(it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio proposta) throws DaoException;

    void rimuoviRichiesta(it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio proposta) throws DaoException;

    List<it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio> getRichiesteRicevute(String usernameDestinatario) throws DaoException;

    List<it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio> getRichiesteInviate(String usernameMittente) throws DaoException;

    List<it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio> getTutteRichieste() throws DaoException;
}
