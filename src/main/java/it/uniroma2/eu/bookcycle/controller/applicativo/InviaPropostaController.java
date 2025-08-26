package it.uniroma2.eu.bookcycle.controller.applicativo;

import it.uniroma2.eu.bookcycle.bean.Proposta2Bean;
import it.uniroma2.eu.bookcycle.bean.PropostaBean;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.GraphicController;
import it.uniroma2.eu.bookcycle.model.dao.ClienteDao;
import it.uniroma2.eu.bookcycle.model.dao.FactoryDao;
import it.uniroma2.eu.bookcycle.model.dao.GestoreLibroScambio;
import it.uniroma2.eu.bookcycle.model.dao.GestoreUtente;
import it.uniroma2.eu.bookcycle.model.dao.PropostaDiScambioDao;
import it.uniroma2.eu.bookcycle.model.eccezioni.*;
import it.uniroma2.eu.bookcycle.model.domain.*;

import java.util.List;

import static it.uniroma2.eu.bookcycle.model.domain.StatoProposta.IN_ATTESA;

public class InviaPropostaController extends GraphicController {
    private PropostaDiScambioDao propostaDiScambioDao;
    GestoreUtente gestore=GestoreUtente.getInstance();

    public InviaPropostaController() throws PersistenzaException {
        this.propostaDiScambioDao = FactoryDao.getIstance().ottieniPropostaDiScambioDao();
    }
    public List<Proposta2Bean> creaListaProposteInviate() {
        String usernameMittente = gestore.getClienteLoggato().getUsername();

        return gestore.trovaProposteInviate(usernameMittente).stream()
            .map(p -> {
            Proposta2Bean bean = new Proposta2Bean();
            bean.setLibroRichiesto(p.getLibroRichiesto().getIdLibro());
            bean.setTitoloRichiesto(p.getLibroRichiesto().getTitolo());
            bean.setLibroOfferto(p.getLibroOfferto().getIdLibro());
            bean.setIdProposta(p.getIdProposta());
            bean.setStato(p.getStato());
            bean.setDestinatario(p.getDestinatario());
                return bean;
        })
                .toList();
    }

    public void inviaProposta(PropostaBean bean) throws BeanInvalidoException, OggettoInvalidoException, PersistenzaException {

        if (!bean.completo()) {
            throw new BeanInvalidoException("Non sono state fornite abbastanza informazioni");
        }
        GestoreUtente gestoreUtente=GestoreUtente.getInstance();
        GestoreLibroScambio gestoreLibroScambio=GestoreLibroScambio.getInstance();
        long idProposta=propostaDiScambioDao.aggiornaIdCounter();

        ClienteDao clienteDao = FactoryDao.getIstance().ottieniClienteDao();
        Utente mittente =gestoreUtente.trovaProprietarioLibro(bean.getLibroOfferto());
        Utente destinatario =gestoreUtente.trovaProprietarioLibro(bean.getLibroRichiesto());


        //gestisco qui la  seconda eccezione
        Libro libroOfferto= null;
        try {
            libroOfferto = gestoreLibroScambio.restituisciLibro(bean.getLibroOfferto());

        } catch (LibroNonTrovatoException e) {

            String prop=gestoreUtente.trovaProprietarioLibro(bean.getLibroOfferto()).getUsername();
            String proprietario=gestoreUtente.restituisciUtente(prop).getUsername();
                List<Libro> simili = gestoreLibroScambio.restituisciSimili(proprietario, bean.getLibroOfferto());

                if (!simili.isEmpty()) {
                    libroOfferto = simili.get(0);
                } else {
                    throw e;
                }
            }

        Libro libroRichiesto=gestoreLibroScambio.restituisciLibro(bean.getLibroRichiesto());
        String destUsername=bean.getDestinatario();
        PropostaDiScambio proposta = new PropostaDiScambio(
                libroOfferto,
                libroRichiesto,
                idProposta,
                IN_ATTESA,
                destUsername
        );

        (mittente).aggiungiPropostaInviata(proposta);
        (destinatario).aggiungiPropostaRicevuta(proposta);

        clienteDao.aggiornaCliente(mittente);
        clienteDao.aggiornaCliente(destinatario);

        propostaDiScambioDao.aggiungiProposta(proposta);

    }
}


