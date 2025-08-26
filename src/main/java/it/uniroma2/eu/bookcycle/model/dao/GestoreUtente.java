package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.bean.ContattiBean;
import it.uniroma2.eu.bookcycle.bean.LibroBean;
import it.uniroma2.eu.bookcycle.model.eccezioni.ClienteNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.domain.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GestoreUtente {

    private static final GestoreUtente instance = new GestoreUtente();




    private GestoreUtente() {

    }


    public static GestoreUtente getInstance() {
        return instance;
    }

    public Utente getClienteLoggato(){
        Cliente cliente= Sessione.ottieniIstanza().getClienteLoggato();
        if(cliente instanceof Utente utente){
            return utente;
        }
        return null;

    }

    public List<LibroBean> caricaLibriUtente(String usernameCliente) throws ClienteNonTrovatoException {
        ClienteDao utenteDao = FactoryDao.getIstance().ottieniClienteDao();
        Cliente cliente = utenteDao.ottieniCliente(usernameCliente);

        if (cliente instanceof Utente utente) {
            List<LibroBean> libri = new ArrayList<>();

            for (Libro l : utente.getLibri()) {
                LibroBean bean = new LibroBean(
                        l.getTitolo(),
                        l.getAutore(),
                        l.getGenere(),
                        l.getIdLibro()
                );
                libri.add(bean);
            }

            return libri;
        }

        return new ArrayList<>();

    }

    public Utente trovaDestinatarioProposta(long idProposta) throws ClienteNonTrovatoException {
        ClienteDao utenteDao = FactoryDao.getIstance().ottieniClienteDao();
        for (Cliente c : utenteDao.getTuttiClienti()) {
            if (c instanceof Utente u) {
                for (PropostaDiScambio p : u.getProposteRicevute()) {
                    if (p.getIdProposta() == idProposta) {
                        return u;
                    }
                }
            }
        }
       throw new ClienteNonTrovatoException("Nessun cliente ha ricevuto questa proposta");
    }

    public Utente trovaMittenteProposta(long idProposta) throws ClienteNonTrovatoException {
        ClienteDao utenteDao = FactoryDao.getIstance().ottieniClienteDao();
        for (Cliente c : utenteDao.getTuttiClienti()) {
            if (c instanceof Utente u) {
                for (PropostaDiScambio p : u.getProposteInviate()) {
                    if (p.getIdProposta() == idProposta) {
                        return u;

                    }
                }
            }
        }
        throw new ClienteNonTrovatoException("Nessun cliente ha inviato questa proposta");
    }



    public Utente trovaProprietarioLibro(long idLibro) {
        ClienteDao utenteDao = FactoryDao.getIstance().ottieniClienteDao();
            for (Cliente c : utenteDao.getTuttiClienti()) {
                if (c instanceof Utente u) {
                    for (Libro l : u.getLibri()) {
                        if (l.getIdLibro() == idLibro) {
                            return u;
                        }
                    }
                }
            }
           return null;
        }





    public List<LibroBean> caricaLibriTuttiAltri() {

        Cliente clienteAttuale = Sessione.ottieniIstanza().getClienteLoggato();
        String usernameCorrente = clienteAttuale.getUsername();

        List<LibroBean> libriDisponibili = new ArrayList<>();
        ClienteDao utenteDao = FactoryDao.getIstance().ottieniClienteDao();

        for (Cliente c : utenteDao.getTuttiClienti()) {
            if (c instanceof Utente) {
                Utente u = (Utente) c;
                if (!u.getUsername().equals(usernameCorrente)) {
                    for (Libro l : u.getLibri()) {
                        LibroBean bean = new LibroBean(l.getTitolo(), l.getAutore(), l.getGenere(), l.getIdLibro());
                        libriDisponibili.add(bean);
                    }
                }
            }

        }
        return libriDisponibili;
    }



    public List<PropostaDiScambio> trovaProposteInviate(String username) {
        try {
            ClienteDao utenteDao = FactoryDao.getIstance().ottieniClienteDao();
            Cliente cliente = utenteDao.ottieniCliente(username);
            if (cliente instanceof Utente utente) {
                return utente.getProposteInviate();
            }
        } catch (ClienteNonTrovatoException e) {
            return Collections.emptyList();
        }
        return Collections.emptyList();

    }

    public List<PropostaDiScambio> trovaProposteRicevute(String username) {
        try {
            ClienteDao utenteDao = FactoryDao.getIstance().ottieniClienteDao();
            Cliente cliente = utenteDao.ottieniCliente(username);
            if (cliente instanceof Utente utente) {
                return utente.getProposteRicevute();
            }
        } catch (ClienteNonTrovatoException e) {
           return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    public void eliminaProposteUtente(String username) {
        try {
            ClienteDao utenteDao = FactoryDao.getIstance().ottieniClienteDao();
            Cliente cliente = utenteDao.ottieniCliente(username);
            if (cliente instanceof Utente utente) {
                utente.getProposteInviate().clear();
                utente.getProposteRicevute().clear();

            }
        } catch (ClienteNonTrovatoException e) {
        }
    }



    // eccezione gestita numero 1
    public List<PropostaDiScambio> caricaProposteUtenteMitente(String usernameCliente) throws  PersistenzaException {
            try {
                ClienteDao utenteDao = FactoryDao.getIstance().ottieniClienteDao();
                Cliente cliente = utenteDao.ottieniCliente(usernameCliente);
                if (cliente.getUsername() == null || cliente.getUsername().isBlank()) {
                    throw new ClienteNonTrovatoException("Cliente con username non valido");
                }
                return trovaProposteInviate(cliente.getUsername());
            } catch (ClienteNonTrovatoException _) {

                List<PropostaDiScambio> sbagliate1 = trovaProposteInviate(usernameCliente);
                List<PropostaDiScambio> sbagliate2 = trovaProposteRicevute(usernameCliente);
                if (!sbagliate1.isEmpty() || !sbagliate2.isEmpty()) {
                    eliminaProposteUtente(usernameCliente);
                }
                return Collections.emptyList();
            }
        }



    public Utente restituisciUtente(String username) throws ClienteNonTrovatoException {
        ClienteDao utenteDao = FactoryDao.getIstance().ottieniClienteDao();
        return (Utente) (utenteDao.trovaPerUsername(username));

    }

    public ContattiBean trovaContattiDaUsername(String username) throws ClienteNonTrovatoException, PersistenzaException {
        ClienteDao utenteDao = FactoryDao.getIstance().ottieniClienteDao();
        String email = utenteDao.trovaEmail(username);
        String tel = utenteDao.trovaTelefono(username);
        if (email == null && tel == null) return null;
        return new ContattiBean(username, tel, email);

    }
}

