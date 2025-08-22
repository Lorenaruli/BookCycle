package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.bean.AnnuncioBean;
import it.uniroma2.eu.bookcycle.model.Eccezioni.ClienteNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.domain.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GestoreLibraio {

    private ClienteDao libraioDao;
    private AnnuncioDao annuncioDao;
    Cliente clienteAttuale = Sessione.ottieniIstanza().getClienteLoggato();

    public GestoreLibraio() throws PersistenzaException {
        this.annuncioDao = FactoryDao.getIstance().ottieniAnnuncioDao();
        this.libraioDao = FactoryDao.getIstance().ottieniClienteDao();


    }



    public List<AnnuncioBean> caricaAnnunciLibraio(String usernameCliente) throws ClienteNonTrovatoException {
        if (clienteAttuale instanceof Libraio) {

            Cliente cliente = libraioDao.ottieniCliente(usernameCliente);
            List<Annuncio> annunciLibraio = annuncioDao.cercaPerProprietario(usernameCliente);
            return annunciLibraio.stream()
                    .map(l -> new AnnuncioBean(l.getLibro().getTitolo(), l.getLibro().getAutore(), l.getPrezzo(), l.getTipo(), l.getIdAnnuncio()))
                    .toList();
        }
        return Collections.emptyList();
    }



    public List<AnnuncioBean> caricaAnnunciTutti() {
        String usernameCorrente = clienteAttuale.getUsername();
        return annuncioDao.ottieniTuttiAnnunci().stream()
                .filter(annuncio -> !Objects.equals(annuncio.getLibraio().getUsername(), usernameCorrente))
                .map(annuncio -> new AnnuncioBean(annuncio.getLibro().getTitolo(), annuncio.getLibro().getAutore(), annuncio.getPrezzo(),annuncio.getTipo(), annuncio.getIdAnnuncio()))
                .toList();
    }



    public Libraio restituisciLibraio(String username) throws ClienteNonTrovatoException {
        Cliente cliente = libraioDao.trovaPerUsername(username);
        return (Libraio) (libraioDao.trovaPerUsername(username));

    }

}



