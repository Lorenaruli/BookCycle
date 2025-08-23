package it.uniroma2.eu.bookcycle.controller.applicativo;

import it.uniroma2.eu.bookcycle.bean.Proposta2Bean;
import it.uniroma2.eu.bookcycle.bean.Proposta3Bean;
import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.dao.ClienteDao;
import it.uniroma2.eu.bookcycle.model.dao.FactoryDao;
import it.uniroma2.eu.bookcycle.model.dao.LibroDao;
import it.uniroma2.eu.bookcycle.model.dao.PropostaDiScambioDao;
import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;
import it.uniroma2.eu.bookcycle.model.domain.StatoProposta;
import it.uniroma2.eu.bookcycle.model.domain.Utente;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GestisciPropostaController {
    private PropostaDiScambioDao propostaDao;
    private ClienteDao clienteDao;
    private LibroDao libroDao;

    public GestisciPropostaController() throws PersistenzaException {
        propostaDao = FactoryDao.getIstance().ottieniPropostaDiScambioDao();
        clienteDao = FactoryDao.getIstance().ottieniClienteDao();
        libroDao = FactoryDao.getIstance().ottieniLibroScambioDao();
    }


    public List<Proposta2Bean> creaListaBeanProposteRicevute(String usernameDestinatario) {
        return propostaDao.getProposteRicevute(usernameDestinatario).stream()
                .filter(p -> p.getDestinatario() != null
                        && usernameDestinatario.equals(p.getDestinatario().getUsername()) && p.getStato()==StatoProposta.IN_ATTESA)
                .map(p -> {
                    Proposta2Bean bean = new Proposta2Bean();
                    bean.setIdProposta(p.getIdProposta());
                    bean.setLibroRichiesto(p.getLibroRichiesto().getIdLibro());
                    bean.setLibroOfferto(p.getLibroOfferto().getIdLibro());
                    bean.setMittente(p.getLibroOfferto().getUsernameProprietario());
                    bean.setDestinatario(p.getLibroRichiesto().getUsernameProprietario());
                    bean.setTitoloOfferto(p.getLibroOfferto().getTitolo());
                    bean.setTitoloRichiesto(p.getLibroRichiesto().getTitolo());
                    return bean;
                })
                .toList();
    }

    public void gestisci(Proposta3Bean propostaBean) throws PersistenzaException, OggettoInvalidoException {
        PropostaDiScambio proposta = propostaDao.cercaPropostaId(propostaBean.getIdProposta());
        if (propostaBean.getStato() == StatoProposta.RIFIUTATA) {
            proposta.setStato(StatoProposta.RIFIUTATA);
            proposta.getDestinatario().rimuoviPropostaRicevuta(propostaBean.getIdProposta());
            propostaDao.aggiungiProposta(proposta);
        }

        if (propostaBean.getStato() == StatoProposta.ACCETTATA) {
            proposta.setStato(StatoProposta.ACCETTATA);
            propostaDao.aggiungiProposta(proposta);

            long idOfferto = proposta.getLibroOfferto().getIdLibro();
            long idRichiesto = proposta.getLibroRichiesto().getIdLibro();

            libroDao.rimuoviLibro(idOfferto);
            libroDao.rimuoviLibro(idRichiesto);

            Utente mittente = (Utente) clienteDao.trovaPerUsername(proposta.getMittente().getUsername());
            mittente.getProposteInviate().stream()
                    .filter(px -> px.getIdProposta() == proposta.getIdProposta())
                    .findFirst()
                    .ifPresent(px -> px.setStato(StatoProposta.ACCETTATA));
            clienteDao.aggiornaCliente(mittente);

            Utente destinatario = (Utente) clienteDao.trovaPerUsername(proposta.getDestinatario().getUsername());
            destinatario.getProposteRicevute().removeIf(px -> px.getIdProposta() == proposta.getIdProposta());
            clienteDao.aggiornaCliente(destinatario);


            List<PropostaDiScambio> conflitti = new ArrayList<>();
            conflitti.addAll(propostaDao.cercaPropostaLibroOfferto(idOfferto));
            conflitti.addAll(propostaDao.cercaPropostaLibroRichiesto(idOfferto));
            conflitti.addAll(propostaDao.cercaPropostaLibroOfferto(idRichiesto));
            conflitti.addAll(propostaDao.cercaPropostaLibroRichiesto(idRichiesto));


            Set<Long> idsVisti = new HashSet<>();
            conflitti.removeIf(p -> !idsVisti.add(p.getIdProposta()));

            for (PropostaDiScambio p : conflitti) {
                if (p.getIdProposta() != proposta.getIdProposta()
                        && p.getStato() == StatoProposta.IN_ATTESA) {

                    p.setStato(StatoProposta.RIFIUTATA);
                    propostaDao.aggiungiProposta(p);

                    Utente m = (Utente) clienteDao.trovaPerUsername(p.getMittente().getUsername());
                    m.getProposteInviate().stream()
                            .filter(px -> px.getIdProposta() == p.getIdProposta())
                            .findFirst()
                            .ifPresent(px -> px.setStato(StatoProposta.RIFIUTATA));
                    clienteDao.aggiornaCliente(m);

                    Utente d = (Utente) clienteDao.trovaPerUsername(p.getDestinatario().getUsername());
                    d.getProposteRicevute().removeIf(px -> px.getIdProposta() == p.getIdProposta());
                    clienteDao.aggiornaCliente(d);
                }

            }
        }
        }
    }

