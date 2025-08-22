package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.bean.ContattiBean;
import it.uniroma2.eu.bookcycle.bean.LibroBean;
import it.uniroma2.eu.bookcycle.model.Eccezioni.ClienteNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.domain.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class GestoreUtente {

    private ClienteDao utenteDao;
    private LibroScambioDao libroScambioDao;
    private PropostaDiScambioDao propostaDao;
    Cliente clienteAttuale = Sessione.ottieniIstanza().getClienteLoggato();

    public GestoreUtente() throws PersistenzaException {
        this.libroScambioDao = FactoryDao.getIstance().ottieniLibroScambioDao();
        this.utenteDao = FactoryDao.getIstance().ottieniClienteDao();
        this.propostaDao = FactoryDao.getIstance().ottieniPropostaDiScambioDao();

    }


    public GestoreUtente(ClienteDao clienteDAO, LibroScambioDao libroScambioDAO) {
        this.utenteDao = clienteDAO;
        this.libroScambioDao = libroScambioDAO;
    }

    public GestoreUtente(ClienteDao clienteDAO, PropostaDiScambioDao propostaDao) throws PersistenzaException {
        this.utenteDao = FactoryDao.getIstance().ottieniClienteDao();
        this.propostaDao = FactoryDao.getIstance().ottieniPropostaDiScambioDao();
    }

    public List<LibroBean> caricaLibriUtente(String usernameCliente) throws ClienteNonTrovatoException {
        if (clienteAttuale instanceof Utente) {

            Cliente cliente = utenteDao.ottieniCliente(usernameCliente);


            List<Libro> libriUtente = libroScambioDao.cercaPerProprietario(usernameCliente);
            return libriUtente.stream()
                    .map(l -> new LibroBean(l.getTitolo(), l.getAutore(), l.getGenere(), l.getIdLibro(), l.getUsernameProprietario()))
                    .toList();
        }
        return Collections.emptyList();
    }


    public List<LibroBean> caricaLibriTutti() {
        String usernameCorrente = clienteAttuale.getUsername();
        return libroScambioDao.getTuttiLibri().stream()
                .filter(libro -> !Objects.equals(libro.getUsernameProprietario(), usernameCorrente))
                .map(libro -> new LibroBean(libro.getTitolo(), libro.getAutore(), libro.getGenere(), libro.getIdLibro(), libro.getUsernameProprietario()))
                .toList();
    }


    // eccezione gestita numero 1
    public List<PropostaDiScambio> caricaProposteUtenteMitente(String usernameCliente) throws  PersistenzaException {
            try {
                Cliente cliente = utenteDao.ottieniCliente(usernameCliente);
                return propostaDao.getProposteInviate(usernameCliente);
            } catch (ClienteNonTrovatoException e) {

                List<PropostaDiScambio> sbagliate1 = propostaDao.getProposteInviate(usernameCliente);
                List<PropostaDiScambio> sbagliate2 = propostaDao.getProposteRicevute(usernameCliente);
                if (!sbagliate1.isEmpty() || !sbagliate2.isEmpty()) {
                    propostaDao.eliminaProposteUtente(usernameCliente);
                }
                return Collections.emptyList();
            }
        }



        public List<PropostaDiScambio> caricaProposteUtenteDestinatario(String usernameCliente) throws ClienteNonTrovatoException {
        Cliente cliente = utenteDao.ottieniCliente(usernameCliente);

        List<PropostaDiScambio> proposteRicevute = propostaDao.getProposteRicevute(usernameCliente);
          return proposteRicevute;


    }
    public Utente restituisciUtente(String username) throws ClienteNonTrovatoException {
        Cliente cliente = utenteDao.trovaPerUsername(username);
        return (Utente) (utenteDao.trovaPerUsername(username));

    }

    public ContattiBean trovaContattiDaUsername(String username) throws ClienteNonTrovatoException, PersistenzaException {
        String email = utenteDao.trovaEmail(username);
        String tel = utenteDao.trovaTelefono(username);
        if (email == null && tel == null) return null;
        return new ContattiBean(username, tel, email);

    }
}

