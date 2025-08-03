package it.uniroma2.eu.bookcycle.controller;

import it.uniroma2.eu.bookcycle.bean.PropostaBean;
import it.uniroma2.eu.bookcycle.model.dao.PropostaDiScambioDao;
import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;
import it.uniroma2.eu.bookcycle.model.domain.Sessione;
import it.uniroma2.eu.bookcycle.model.domain.Utente;

public class InviaPropostaController {
    private PropostaDiScambioDao propostaDiScambioDao;

    public InviaPropostaController() {
        this.propostaDiScambioDao = propostaDiScambioDao;
    }

    public void InviaProposta(PropostaBean bean) {
        if (!bean.completo()) {
            throw new RuntimeException("non sono state fornite abbastanza informazioni");
        }
        PropostaDiScambio proposta = new PropostaDiScambio(bean.getMittente(), bean.getDestinatario(), bean.getLibroOfferto(), bean.getLibroRichiesto());
        propostaDiScambioDao.salvaRichiesta(proposta);

//        if (bean.getMittente() instanceof Utente) {
//            ((Utente)bean.getMittente).aggiungiProposteInviate();
//            libroDao.aggiungiLibro(libro);
//
//            Utente utente= (Utente) gestoreUtente.caricaLibriUtente(Sessione.ottieniIstanza().getClienteLoggato().getUsername());}
//        else
//            throw new RuntimeException("il cliente non è utente");
        // tramite la funzioneGestore devi aggiungere le proposteinviate all'utente che le invia
        // e quelle ricevute a chi riceve
    }



}
