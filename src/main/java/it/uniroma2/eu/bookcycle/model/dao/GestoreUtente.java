package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.domain.Cliente;
import it.uniroma2.eu.bookcycle.model.domain.Libro;
import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;
import it.uniroma2.eu.bookcycle.model.domain.Utente;

import java.util.List;

public class GestoreUtente {

    private ClienteDao utenteDao;
    private LibroScambioDao libroScambioDao;
    private PropostaDiScambioDao propostaDao;

    public GestoreUtente(){
        this.libroScambioDao = FactoryDao.getIstance().ottieniLibroScambioDao();
        this.utenteDao = FactoryDao.getIstance().ottieniClienteDao();

    }


    public GestoreUtente(ClienteDao clienteDAO, LibroScambioDao libroScambioDAO) {
        this.utenteDao = clienteDAO;
        this.libroScambioDao = libroScambioDAO;
    }
    public GestoreUtente(ClienteDao clienteDAO, PropostaDiScambioDao propostaDao) {
        this.utenteDao = clienteDAO;
        this.propostaDao = propostaDao;
    }

    public List<Libro> caricaLibriUtente(String usernameCliente) {
        Cliente cliente = utenteDao.ottieniCliente(usernameCliente);

        return libroScambioDao.cercaPerProprietario(usernameCliente);

    }

    public List<Libro> caricaLibriTutti(){
        return libroScambioDao.getLibriDisponibili();
    }

    public void caricaProposteUtenteMitente(String usernameCliente) {
        Cliente cliente = utenteDao.ottieniCliente(usernameCliente);
        if (cliente instanceof Utente) {
            List<PropostaDiScambio> proposteInviate = propostaDao.getProposteInviate(usernameCliente);
            //((Utente) cliente).aggiungiProposteInviate(proposteInviate);
        }
    }

    public void caricaProposteUtenteDestinatario(String usernameCliente) {
        Cliente cliente = utenteDao.ottieniCliente(usernameCliente);
        if (cliente instanceof Utente) {
            List<PropostaDiScambio> proposteRicevute = propostaDao.getProposteRicevute(usernameCliente);
           // ((Utente) cliente).aggiungiProposteRicevute(proposteRicevute);

        }
    }
    public Utente restituisciUtente(String username){
         utenteDao.trovaPerUsername(username);
         return (Utente)(utenteDao.trovaPerUsername(username));

    }
}

