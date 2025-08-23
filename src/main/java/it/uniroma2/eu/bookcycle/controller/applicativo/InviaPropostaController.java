package it.uniroma2.eu.bookcycle.controller.applicativo;

import it.uniroma2.eu.bookcycle.bean.Proposta4Bean;
import it.uniroma2.eu.bookcycle.bean.PropostaBean;
import it.uniroma2.eu.bookcycle.model.Eccezioni.*;
import it.uniroma2.eu.bookcycle.model.dao.FactoryDao;
import it.uniroma2.eu.bookcycle.model.dao.GestoreLibroScambio;
import it.uniroma2.eu.bookcycle.model.dao.GestoreUtente;
import it.uniroma2.eu.bookcycle.model.dao.PropostaDiScambioDao;
import it.uniroma2.eu.bookcycle.model.domain.*;

import java.util.List;

import static it.uniroma2.eu.bookcycle.model.domain.StatoProposta.IN_ATTESA;

public class InviaPropostaController {
    private PropostaDiScambioDao propostaDiScambioDao;

    public InviaPropostaController() throws PersistenzaException {
        this.propostaDiScambioDao = FactoryDao.getIstance().ottieniPropostaDiScambioDao();
    }
    public List<Proposta4Bean> creaListaBeanProposteInviate() {
        String usernameMittente = Sessione.ottieniIstanza().getClienteLoggato().getUsername();
        return propostaDiScambioDao.getProposteInviate(usernameMittente).stream()
                .map(p -> {
                    Proposta4Bean bean = new Proposta4Bean();
                    bean.setTitoloOfferto(p.getLibroOfferto().getTitolo());
                    bean.setUsernameDestinatario(p.getDestinatario().getUsername());
                    bean.setIdProposta(p.getIdProposta());
                    bean.setStato(p.getStato());
                    return bean;
                })
                .toList();
    }

    public void inviaProposta(PropostaBean bean) throws BeanInvalidoException, RuoloClienteException, OggettoInvalidoException, PersistenzaException {

        if (!bean.completo()) {
            throw new BeanInvalidoException("Non sono state fornite abbastanza informazioni");
        }
        GestoreUtente gestoreUtente= new GestoreUtente();
        GestoreLibroScambio gestoreLibroScambio= new GestoreLibroScambio();
        long idProposta=propostaDiScambioDao.aggiornaIdCounter();

        Cliente mittente=gestoreUtente.restituisciUtente(bean.getMittente());
        Cliente destinatario=gestoreUtente.restituisciUtente(bean.getDestinatario());


        if ((!(mittente instanceof Utente)) || (!(destinatario instanceof Utente)) ){
            throw new RuoloClienteException("Entrambi i clienti devono essere utenti");
       }

        //gestisco qui la  seconda eccezione
        Libro libroOfferto= null;
        try {
            libroOfferto = gestoreLibroScambio.restituisciLibro(bean.getLibroOfferto());
        } catch (LibroNonTrovatoException e) {

                List<Libro> simili = gestoreLibroScambio.restituisciSimili(bean.getMittente(), bean.getLibroOfferto());

                if (!simili.isEmpty()) {
                    libroOfferto = simili.get(0);
                } else {
                    throw e;
                }
            }

        Libro libroRichiesto=gestoreLibroScambio.restituisciLibro(bean.getLibroRichiesto());
        PropostaDiScambio proposta = new PropostaDiScambio(
                (Utente)mittente,
                (Utente)destinatario,
                libroRichiesto,
                libroOfferto,
                idProposta,
                IN_ATTESA

        );


        propostaDiScambioDao.aggiungiProposta(proposta);



        ((Utente)mittente).aggiungiPropostaInviata(proposta);
        ((Utente)destinatario).aggiungiPropostaRicevuta(proposta);
    }
}


