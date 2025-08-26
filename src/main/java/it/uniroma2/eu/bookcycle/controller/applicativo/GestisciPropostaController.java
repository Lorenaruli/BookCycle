package it.uniroma2.eu.bookcycle.controller.applicativo;

import it.uniroma2.eu.bookcycle.bean.Proposta2Bean;
import it.uniroma2.eu.bookcycle.bean.Proposta3Bean;
import it.uniroma2.eu.bookcycle.model.dao.*;
import it.uniroma2.eu.bookcycle.model.eccezioni.ClienteNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;
import it.uniroma2.eu.bookcycle.model.domain.StatoProposta;
import it.uniroma2.eu.bookcycle.model.domain.Utente;

import java.util.ArrayList;
import java.util.List;

public class GestisciPropostaController {
    private PropostaDiScambioDao propostaDao;
    private ClienteDao clienteDao;
    private LibroDao libroDao;
    private GestoreUtente gestore = GestoreUtente.getInstance();
    private long idRichiesto;
    private long idOfferto;



    public GestisciPropostaController() throws PersistenzaException {
        propostaDao = FactoryDao.getIstance().ottieniPropostaDiScambioDao();
        clienteDao = FactoryDao.getIstance().ottieniClienteDao();
        libroDao = FactoryDao.getIstance().ottieniLibroScambioDao();
    }


    public List<Proposta2Bean> creaListaBeanProposteRicevute(String usernameDestinatario) {
        List<Proposta2Bean> risultati = new ArrayList<>();
        List<PropostaDiScambio> proposte = gestore.trovaProposteRicevute(usernameDestinatario);

        for (PropostaDiScambio p : proposte) {
            if (p.getStato()==StatoProposta.IN_ATTESA){

                Proposta2Bean bean = new Proposta2Bean();
                bean.setIdProposta(p.getIdProposta());
                bean.setLibroRichiesto(p.getLibroRichiesto().getIdLibro());
                bean.setLibroOfferto(p.getLibroOfferto().getIdLibro());

                risultati.add(bean);
            }
        }

        return risultati;
    }


    public void gestisci(Proposta3Bean propostaBean) throws PersistenzaException, OggettoInvalidoException {
        PropostaDiScambio proposta = propostaDao.cercaPropostaId(propostaBean.getIdProposta());
        Utente mittente = gestore.trovaMittenteProposta(proposta.getIdProposta());
        Utente destinatario = gestore.trovaDestinatarioProposta(propostaBean.getIdProposta());

        if (propostaBean.getStato() == StatoProposta.RIFIUTATA) {
            proposta.setStato(StatoProposta.RIFIUTATA);
            destinatario.rimuoviPropostaRicevuta(propostaBean.getIdProposta());

            clienteDao.aggiornaCliente(destinatario);

            for (PropostaDiScambio px : mittente.getProposteInviate()) {
                if (px.getIdProposta() == proposta.getIdProposta()) {
                    px.setStato(StatoProposta.RIFIUTATA);
                    break;
                }
            }

            clienteDao.aggiornaCliente(mittente);
            propostaDao.aggiungiProposta(proposta);
        }

        if (propostaBean.getStato() == StatoProposta.ACCETTATA) {
            proposta.setStato(StatoProposta.ACCETTATA);
            propostaDao.aggiungiProposta(proposta);

            this.idOfferto = proposta.getLibroOfferto().getIdLibro();
            this.idRichiesto = proposta.getLibroRichiesto().getIdLibro();

            mittente.eliminaLibro(idOfferto);
            destinatario.eliminaLibro(idRichiesto);

            for (PropostaDiScambio px : mittente.getProposteInviate()) {
                if (px.getIdProposta() == proposta.getIdProposta()) {
                    px.setStato(StatoProposta.ACCETTATA);
                    break;
                }
            }
            clienteDao.aggiornaCliente(mittente);

            destinatario.rimuoviPropostaRicevuta(propostaBean.getIdProposta());

            clienteDao.aggiornaCliente(destinatario);

            propostaDao.aggiungiProposta(proposta);


            List<PropostaDiScambio> conflitti = new ArrayList<>();
            conflitti.addAll(propostaDao.cercaPropostaLibroOfferto(idOfferto));
            conflitti.addAll(propostaDao.cercaPropostaLibroRichiesto(idOfferto));
            conflitti.addAll(propostaDao.cercaPropostaLibroOfferto(idRichiesto));
            conflitti.addAll(propostaDao.cercaPropostaLibroRichiesto(idRichiesto));
            conflitti.remove(proposta);

            risolviConflitti(conflitti);

            libroDao.rimuoviLibro(idOfferto);
            libroDao.rimuoviLibro(idRichiesto);
        }
    }


    public void risolviConflitti(List<PropostaDiScambio> conflitti) {
        for (PropostaDiScambio p : conflitti) {

            if (p.getLibroOfferto().getIdLibro() == idOfferto ||
                    p.getLibroOfferto().getIdLibro() == idRichiesto||
                    p.getLibroRichiesto().getIdLibro() == idOfferto ||
                    p.getLibroRichiesto().getIdLibro() == idRichiesto) {
                try {
                    Utente destinatario = gestore.trovaDestinatarioProposta(p.getIdProposta());
                    Utente mittente = gestore.trovaMittenteProposta(p.getIdProposta());

                    p.setStato(StatoProposta.RIFIUTATA);

                    for (PropostaDiScambio px : mittente.getProposteInviate()) {
                        if (px.getIdProposta() == p.getIdProposta()) {
                            px.setStato(StatoProposta.RIFIUTATA);
                            break;
                        }
                    }

                    destinatario.rimuoviPropostaRicevuta(p.getIdProposta());
                    clienteDao.aggiornaCliente(destinatario);
                    clienteDao.aggiornaCliente(mittente);
                    propostaDao.aggiungiProposta(p);


                } catch (ClienteNonTrovatoException _)  {
                    continue;
                } catch (OggettoInvalidoException _)  {
                    break;
            }
            }



            }
        }
    }











