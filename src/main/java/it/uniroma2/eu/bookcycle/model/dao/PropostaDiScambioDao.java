package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;

import java.util.List;

public interface PropostaDiScambioDao {

    void aggiungiProposta(PropostaDiScambio proposta) throws DaoException;

    void rimuoviProposta(long idProposta) throws DaoException;
    long aggiornaIdCounter();

    List<PropostaDiScambio> getProposteRicevute(String usernameDestinatario) throws DaoException;

    List<PropostaDiScambio> getProposteInviate(String usernameMittente) throws DaoException;

    List<PropostaDiScambio> cercaPropostaLibroOfferto(long idLibro);

    List<PropostaDiScambio> cercaPropostaLibroRichiesto(long idLibro);

    PropostaDiScambio cercaPropostaId(long idProposta);




}
