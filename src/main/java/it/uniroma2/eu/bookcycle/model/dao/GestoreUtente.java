package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.bean.ContattiBean;
import it.uniroma2.eu.bookcycle.bean.LibroBean;
import it.uniroma2.eu.bookcycle.model.domain.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GestoreUtente {

    private ClienteDao utenteDao;
    private LibroScambioDao libroScambioDao;
    private PropostaDiScambioDao propostaDao;
    Cliente clienteAttuale = Sessione.ottieniIstanza().getClienteLoggato();

    public GestoreUtente(){
        this.libroScambioDao = FactoryDao.getIstance().ottieniLibroScambioDao();
        this.utenteDao = FactoryDao.getIstance().ottieniClienteDao();
        this.propostaDao = FactoryDao.getIstance().ottieniPropostaDiScambioDao();

    }


    public GestoreUtente(ClienteDao clienteDAO, LibroScambioDao libroScambioDAO) {
        this.utenteDao = clienteDAO;
        this.libroScambioDao = libroScambioDAO;
    }
    public GestoreUtente(ClienteDao clienteDAO, PropostaDiScambioDao propostaDao) {
        this.utenteDao = FactoryDao.getIstance().ottieniClienteDao();
        this.propostaDao = FactoryDao.getIstance().ottieniPropostaDiScambioDao();
    }

    public List<LibroBean> caricaLibriUtente(String usernameCliente) {
        if(clienteAttuale instanceof Utente) {

            Cliente cliente = utenteDao.ottieniCliente(usernameCliente);


        List<Libro> libriUtente= libroScambioDao.cercaPerProprietario(usernameCliente);
            return libriUtente.stream()
                    .map(l -> new LibroBean(l.getTitolo(),l.getAutore(),l.getGenere(),l.getIdLibro(),l.getUsernameProprietario(),l.getStato()))
                    .toList();
        }
        return Collections.emptyList();
    }





//    public List<Libro> caricaLibriTutti(){
//        return libroScambioDao.getLibriDisponibili();
//    }

    public List<LibroBean> caricaLibriTutti() {
        String usernameCorrente= clienteAttuale.getUsername();
        return libroScambioDao.getLibriDisponibili().stream()
                .filter(libro -> !Objects.equals(libro.getUsernameProprietario(), usernameCorrente))
                .map(libro -> new LibroBean(libro.getTitolo(),libro.getAutore(),libro.getGenere(),libro.getIdLibro(), libro.getUsernameProprietario(), libro.getStato()))
                .toList();
    }

    public List<PropostaDiScambio> caricaProposteUtenteMitente(String usernameCliente) {
        Cliente cliente = utenteDao.ottieniCliente(usernameCliente);
            List<PropostaDiScambio> proposteInviate = propostaDao.getProposteInviate(usernameCliente);
        return proposteInviate;
    }

    public void caricaProposteUtenteDestinatario(String usernameCliente) {
        Cliente cliente = utenteDao.ottieniCliente(usernameCliente);
        if (cliente instanceof Utente) {
            List<PropostaDiScambio> proposteRicevute = propostaDao.getProposteRicevute(usernameCliente);
           // ((Utente) cliente).aggiungiProposteRicevute(proposteRicevute);

        }
    }
    public Utente restituisciUtente(String username){
        System.out.println("Cerco utente con username: " + username);
        Cliente cliente = utenteDao.trovaPerUsername(username);
        System.out.println("Risultato ottenuto dal DAO: " + cliente);
         return (Utente)(utenteDao.trovaPerUsername(username));

    }

    public ContattiBean getContattiByUsername(String username) {
        String email = utenteDao.trovaEmail(username);
        String tel   = utenteDao.trovaTelefono(username);
        if (email == null && tel == null) return null;
        return new ContattiBean(username,tel, email);
    }
}

