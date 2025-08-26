package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.bean.AnnuncioBean;
import it.uniroma2.eu.bookcycle.model.domain.Cliente;
import it.uniroma2.eu.bookcycle.model.domain.Libraio;
import it.uniroma2.eu.bookcycle.model.domain.Sessione;
import it.uniroma2.eu.bookcycle.model.eccezioni.ClienteNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;


import java.util.Collections;
import java.util.List;


public class GestoreLibraio {
    private static final GestoreLibraio instance = new GestoreLibraio();



    private GestoreLibraio() throws PersistenzaException {


    }

    public static GestoreLibraio getInstance() {
        return instance;
    }


    public List<AnnuncioBean> caricaAnnunciLibraio() {



        Cliente clienteAttuale = Sessione.ottieniIstanza().getClienteLoggato();
if(clienteAttuale instanceof
    Libraio libraio)

    {
        return libraio.getAnnunci().stream()
                .map(a -> new AnnuncioBean(
                        a.getLibro().getTitolo(),
                        a.getLibro().getAutore(),
                        a.getPrezzo(),
                        a.getTipo(),
                        a.getIdAnnuncio()))
                .toList();

    }
return Collections.emptyList();
}


public List<AnnuncioBean> caricaAnnunciTutti() {
    Cliente clienteAttuale = Sessione.ottieniIstanza().getClienteLoggato();
    AnnuncioDao annuncioDao= FactoryDao.getIstance().ottieniAnnuncioDao();
    return annuncioDao.ottieniTuttiAnnunci().stream()
            .map(annuncio -> new AnnuncioBean(annuncio.getLibro().getTitolo(), annuncio.getLibro().getAutore(), annuncio.getPrezzo(),annuncio.getTipo(), annuncio.getIdAnnuncio()))
            .toList();
}



    public Libraio restituisciLibraio(String username) throws ClienteNonTrovatoException {
        ClienteDao libraioDao=FactoryDao.getIstance().ottieniClienteDao();
        return (Libraio) (libraioDao.trovaPerUsername(username));

    }

}



