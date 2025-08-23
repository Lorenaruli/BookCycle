package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.bean.ContattiBean;
import it.uniroma2.eu.bookcycle.bean.LibroBean;
import it.uniroma2.eu.bookcycle.model.eccezioni.ClienteNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
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


    public List<LibroBean> caricaLibriUtente(String usernameCliente) throws ClienteNonTrovatoException {
        if (clienteAttuale instanceof Utente) {



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
                if (cliente.getUsername() == null || cliente.getUsername().isBlank()) {
                    throw new ClienteNonTrovatoException("Cliente con username non valido");
                }
                return propostaDao.getProposteInviate(cliente.getUsername());
            } catch (ClienteNonTrovatoException _) {

                List<PropostaDiScambio> sbagliate1 = propostaDao.getProposteInviate(usernameCliente);
                List<PropostaDiScambio> sbagliate2 = propostaDao.getProposteRicevute(usernameCliente);
                if (!sbagliate1.isEmpty() || !sbagliate2.isEmpty()) {
                    propostaDao.eliminaProposteUtente(usernameCliente);
                }
                return Collections.emptyList();
            }
        }



        public List<PropostaDiScambio> caricaProposteUtenteDestinatario(String usernameCliente) {

        return propostaDao.getProposteRicevute(usernameCliente);



    }
    public Utente restituisciUtente(String username) throws ClienteNonTrovatoException {
        return (Utente) (utenteDao.trovaPerUsername(username));

    }

    public ContattiBean trovaContattiDaUsername(String username) throws ClienteNonTrovatoException, PersistenzaException {
        String email = utenteDao.trovaEmail(username);
        String tel = utenteDao.trovaTelefono(username);
        if (email == null && tel == null) return null;
        return new ContattiBean(username, tel, email);

    }
}

