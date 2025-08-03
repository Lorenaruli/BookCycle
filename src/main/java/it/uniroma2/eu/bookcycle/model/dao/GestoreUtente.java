package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.domain.Cliente;
import it.uniroma2.eu.bookcycle.model.domain.Libro;
import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;
import it.uniroma2.eu.bookcycle.model.domain.Utente;

import java.util.List;

public class GestoreUtente {
    private ClienteDao utenteDao;
    private LibroScambioDao libroScambioDAO;
    private PropostaDiScambioDao propostaDao;


    public GestoreUtente(ClienteDao clienteDAO, LibroScambioDao libroScambioDAO) {
        this.utenteDao = clienteDAO;
        this.libroScambioDAO = libroScambioDAO;
    }

    public Cliente caricaLibriUtente(String usernameCliente) {
        Cliente cliente = utenteDao.ottieniCliente(usernameCliente);

        if (cliente instanceof Utente) {
            Utente utente = (Utente) cliente;
            List<Libro> libriDellUtente = libroScambioDAO.cercaPerProprietario(usernameCliente);
            utente.setLibri(libriDellUtente);
        }

        return cliente;
    }

    public void caricaProposteUtenteMitente(String usernameCliente) {
        Cliente cliente = utenteDao.ottieniCliente(usernameCliente);
        if (cliente instanceof Utente) {
            List<PropostaDiScambio> proposteInviate = propostaDao.getProposteInviate(usernameCliente);
            ((Utente) cliente).aggiungiProposteInviate(proposteInviate);
        }
    }

    public void caricaProposteUtenteDestinatario(String usernameCliente) {
        Cliente cliente = utenteDao.ottieniCliente(usernameCliente);
        if (cliente instanceof Utente) {
            List<PropostaDiScambio> proposteRicevute = propostaDao.getProposteRicevute(usernameCliente);
            ((Utente) cliente).aggiungiProposteRicevute(proposteRicevute);

        }
    }
}

