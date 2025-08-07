package it.uniroma2.eu.bookcycle.controller;

import it.uniroma2.eu.bookcycle.bean.PropostaBean;
import it.uniroma2.eu.bookcycle.model.dao.FactoryDao;
import it.uniroma2.eu.bookcycle.model.dao.GestoreLibroScambio;
import it.uniroma2.eu.bookcycle.model.dao.GestoreUtente;
import it.uniroma2.eu.bookcycle.model.dao.PropostaDiScambioDao;
import it.uniroma2.eu.bookcycle.model.domain.*;

import static it.uniroma2.eu.bookcycle.model.domain.StatoProposta.IN_ATTESA;

public class InviaPropostaController {
    private PropostaDiScambioDao propostaDiScambioDao;

    public InviaPropostaController() {
        this.propostaDiScambioDao = FactoryDao.getIstance().ottieniPropostaDiScambioDao();
    }

    public void inviaProposta(PropostaBean bean) {
        System.out.println("[DEBUG Controller] Mittente: " + bean.getMittente());
        System.out.println("[DEBUG Controller] Destinatario: " + bean.getDestinatario());
        System.out.println("[DEBUG Controller] Libro richiesto: " + bean.getLibroRichiesto());
        System.out.println("[DEBUG Controller] Libro offerto: " + bean.getLibroOfferto());

        if (!bean.completo()) {
            throw new RuntimeException("Non sono state fornite abbastanza informazioni");
        }
        GestoreUtente gestoreUtente= new GestoreUtente();
        GestoreLibroScambio gestoreLibroScambio= new GestoreLibroScambio();
        long idProposta=propostaDiScambioDao.aggiornaIdCounter();

        Cliente mittente=gestoreUtente.restituisciUtente(bean.getMittente());
        Cliente destinatario=gestoreUtente.restituisciUtente(bean.getDestinatario());


        if ((!(mittente instanceof Utente)) || (!(destinatario instanceof Utente)) ){
            throw new RuntimeException("Entrambi i clienti devono essere utenti");
       }
        Libro libroOfferto= gestoreLibroScambio.restituisciLibro(bean.getLibroOfferto());
        Libro libroRichiesto=gestoreLibroScambio.restituisciLibro(bean.getLibroRichiesto());
        PropostaDiScambio proposta = new PropostaDiScambio(
                (Utente)mittente,
                (Utente)destinatario,
                libroRichiesto,
                libroOfferto,
                idProposta,
                IN_ATTESA

        );


        propostaDiScambioDao.aggiungiRichiesta(proposta);


        String destinatarioUsername = bean.getDestinatario();
        String mittenteUsername = bean.getMittente();


        ((Utente)mittente).aggiungiPropostaInviata(proposta);
        ((Utente)destinatario).aggiungiPropostaRicevuta(proposta);
    }
}


