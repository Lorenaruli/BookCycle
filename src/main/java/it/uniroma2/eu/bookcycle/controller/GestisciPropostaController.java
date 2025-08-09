package it.uniroma2.eu.bookcycle.controller;

import it.uniroma2.eu.bookcycle.bean.Proposta2Bean;
import it.uniroma2.eu.bookcycle.bean.Proposta3Bean;
import it.uniroma2.eu.bookcycle.model.dao.ClienteDao;
import it.uniroma2.eu.bookcycle.model.dao.FactoryDao;
import it.uniroma2.eu.bookcycle.model.dao.PropostaDiScambioDao;
import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;
import it.uniroma2.eu.bookcycle.model.domain.StatoProposta;

import java.util.List;

public class GestisciPropostaController {
    PropostaDiScambioDao propostaDao = FactoryDao.getIstance().ottieniPropostaDiScambioDao();
    ClienteDao clienteDao = FactoryDao.getIstance().ottieniClienteDao();


    public List<Proposta2Bean> creaListaBeanProposteRicevute(String usernameDestinatario) {
        return propostaDao.getProposteRicevute(usernameDestinatario).stream()
                .filter(p -> p.getDestinatario() != null
                        && usernameDestinatario.equals(p.getDestinatario().getUsername()))
                .map(p -> {
                    Proposta2Bean bean = new Proposta2Bean();
                    bean.setIdProposta(p.getIdProposta());
                    bean.setIdLibroR(p.getLibroRichiesto().getIdLibro());
                    bean.setIdLibroO(p.getLibroOfferto().getIdLibro());
                    bean.setUsernameM(p.getLibroOfferto().getUsernameProprietario());
                    bean.setUsernameD(p.getLibroRichiesto().getUsernameProprietario());
                    bean.setTitoloOfferto(p.getLibroOfferto().getTitolo());
                    bean.setTitoloRichiesto(p.getLibroRichiesto().getTitolo());
                    return bean;
                })
                .toList();
    }

    public void gestisci(Proposta3Bean propostaBean) {
        PropostaDiScambio proposta = propostaDao.cercaPropostaId(propostaBean.getIdProposta());
        proposta.setStato(propostaBean.getStato());
        if (propostaBean.getStato() == StatoProposta.RIFIUTATA) {
            proposta.setStato(StatoProposta.RIFIUTATA);
            proposta.getDestinatario().rimuoviPropostaRicevuta(propostaBean.getIdProposta());
            propostaDao.aggiungiRichiesta(proposta);
        }
        if (propostaBean.getStato() == StatoProposta.ACCETTATA) {
            //forse devo gestire proposte concorrentemente accettate?
            propostaDao.aggiungiRichiesta(proposta);
            proposta.getDestinatario().eliminaLibro(proposta.getLibroRichiesto().getIdLibro());
            proposta.getMittente().eliminaLibro(proposta.getLibroOfferto().getIdLibro());//forse ho invertito libri
            List<PropostaDiScambio> proposteDaRifiutare1 = propostaDao.cercaPropostaLibroRichiesto(proposta.getLibroOfferto().getIdLibro());
            List<PropostaDiScambio> proposteDaRifiutare2 = propostaDao.cercaPropostaLibroOfferto(proposta.getLibroRichiesto().getIdLibro());//forse ho di nuovo invertito i libri

            for (PropostaDiScambio p1 : proposteDaRifiutare1) {
                if (StatoProposta.IN_ATTESA==(p1.getStato())) {
                    p1.setStato(StatoProposta.RIFIUTATA);
                    propostaDao.aggiungiRichiesta(p1);

                }
            }

            for (PropostaDiScambio p2  : proposteDaRifiutare2) {
                if (StatoProposta.IN_ATTESA==(p2.getStato())) {
                    propostaDao.rimuoviRichiesta(p2.getIdProposta());
                }
            }
        }
    }
}
