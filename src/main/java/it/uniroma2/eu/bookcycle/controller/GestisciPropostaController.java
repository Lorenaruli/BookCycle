package it.uniroma2.eu.bookcycle.controller;

import it.uniroma2.eu.bookcycle.bean.Proposta2Bean;
import it.uniroma2.eu.bookcycle.bean.Proposta3Bean;
import it.uniroma2.eu.bookcycle.model.dao.ClienteDao;
import it.uniroma2.eu.bookcycle.model.dao.FactoryDao;
import it.uniroma2.eu.bookcycle.model.dao.LibroDao;
import it.uniroma2.eu.bookcycle.model.dao.PropostaDiScambioDao;
import it.uniroma2.eu.bookcycle.model.domain.Cliente;
import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;
import it.uniroma2.eu.bookcycle.model.domain.StatoProposta;
import it.uniroma2.eu.bookcycle.model.domain.Utente;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GestisciPropostaController {
    PropostaDiScambioDao propostaDao = FactoryDao.getIstance().ottieniPropostaDiScambioDao();
    ClienteDao clienteDao = FactoryDao.getIstance().ottieniClienteDao();
    LibroDao libroDao= FactoryDao.getIstance().ottieniLibroScambioDao();


    public List<Proposta2Bean> creaListaBeanProposteRicevute(String usernameDestinatario) {
        return propostaDao.getProposteRicevute(usernameDestinatario).stream()
                .filter(p -> p.getDestinatario() != null
                        && usernameDestinatario.equals(p.getDestinatario().getUsername()) && p.getStato()==StatoProposta.IN_ATTESA)
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
//        if (propostaBean.getStato() == StatoProposta.ACCETTATA) {
//            //forse devo gestire proposte concorrentemente accettate?
//            proposta.setStato(StatoProposta.ACCETTATA);
//            propostaDao.aggiungiRichiesta(proposta);
//            libroDao.rimuoviLibro(proposta.getLibroOfferto().getIdLibro());
//            libroDao.rimuoviLibro(proposta.getLibroRichiesto().getIdLibro());
//
//            Cliente cliente = clienteDao.trovaPerUsername(proposta.getMittente().getUsername());
//            Utente mittente=(Utente)cliente;
//            mittente.getProposteInviate().stream()
//                    .filter(p -> java.util.Objects.equals(p.getIdProposta(), proposta.getIdProposta()))
//                    .findFirst()
//                    .ifPresent(p -> p.setStato(proposta.getStato()));
//            clienteDao.aggiornaCliente(mittente);
//
//            Cliente cliente2 = clienteDao.trovaPerUsername(proposta.getDestinatario().getUsername());
//            Utente destinatario = (Utente)cliente2;
//            destinatario.getProposteRicevute().removeIf(
//                    p -> java.util.Objects.equals(p.getIdProposta(), proposta.getIdProposta())
//            );
//            clienteDao.aggiornaCliente(destinatario);
//            //proposta.getDestinatario().rimuoviPropostaRicevuta(propostaBean.getIdProposta());
//
//
//
////            proposta.getDestinatario().eliminaLibro(proposta.getLibroOfferto().getIdLibro());
////            proposta.getMittente().eliminaLibro(proposta.getLibroRichiesto().getIdLibro());
//
//            //forse ho invertito libri
//            List<PropostaDiScambio> proposteDaRifiutare1 = propostaDao.cercaPropostaLibroRichiesto(proposta.getLibroOfferto().getIdLibro());
//            List<PropostaDiScambio> proposteDaRifiutare2 = propostaDao.cercaPropostaLibroRichiesto(proposta.getLibroRichiesto().getIdLibro());
//            List<PropostaDiScambio> proposteDaEliminare1 = propostaDao.cercaPropostaLibroOfferto(proposta.getLibroRichiesto().getIdLibro());//forse ho di nuovo invertito i libri
//            List<PropostaDiScambio> proposteDaEliminare2= propostaDao.cercaPropostaLibroOfferto(proposta.getLibroOfferto().getIdLibro());
//
//            for (PropostaDiScambio p1 : proposteDaRifiutare1) {
//                if (StatoProposta.IN_ATTESA==(p1.getStato())) {
//                    p1.setStato(StatoProposta.RIFIUTATA);
//                    propostaDao.aggiungiRichiesta(p1);
//
//                }
//            }
//
//            for (PropostaDiScambio p1 : proposteDaRifiutare2) {
//                if (StatoProposta.IN_ATTESA==(p1.getStato())) {
//                    p1.setStato(StatoProposta.RIFIUTATA);
//                    propostaDao.aggiungiRichiesta(p1);
//
//                }
//            }
//
//            for (PropostaDiScambio p2  : proposteDaEliminare1) {
//                if (StatoProposta.IN_ATTESA==(p2.getStato())) {
//                    propostaDao.rimuoviRichiesta(p2.getIdProposta());
//                }
//            }
//            for (PropostaDiScambio p2  : proposteDaEliminare2) {
//                if (StatoProposta.IN_ATTESA == (p2.getStato())) {
//                    propostaDao.rimuoviRichiesta(p2.getIdProposta());
//
//                }
//            }
//            mittente.getProposteInviate().stream()
//                    .filter(p -> java.util.Objects.equals(p.getIdProposta(), proposta.getIdProposta()))
//                    .findFirst()
//                    .ifPresent(p -> p.setStato(proposta.getStato()));
//            clienteDao.aggiornaCliente(mittente);
//
//            destinatario.getProposteRicevute().removeIf(
//                    p -> java.util.Objects.equals(p.getIdProposta(), proposta.getIdProposta())
//            );
//            clienteDao.aggiornaCliente(destinatario);
//        }
//    }
        if (propostaBean.getStato() == StatoProposta.ACCETTATA) {
            proposta.setStato(StatoProposta.ACCETTATA);
            propostaDao.aggiungiRichiesta(proposta);

            long idOfferto   = proposta.getLibroOfferto().getIdLibro();
            long idRichiesto = proposta.getLibroRichiesto().getIdLibro();

            // Libri non più scambiabili
            libroDao.rimuoviLibro(idOfferto);
            libroDao.rimuoviLibro(idRichiesto);

            // Aggiorna mittente e destinatario della proposta accettata
            Utente mittente = (Utente) clienteDao.trovaPerUsername(proposta.getMittente().getUsername());
            mittente.getProposteInviate().stream()
                    .filter(px -> px.getIdProposta() == proposta.getIdProposta())
                    .findFirst()
                    .ifPresent(px -> px.setStato(StatoProposta.ACCETTATA));
            clienteDao.aggiornaCliente(mittente);

            Utente destinatario = (Utente) clienteDao.trovaPerUsername(proposta.getDestinatario().getUsername());
            destinatario.getProposteRicevute().removeIf(px -> px.getIdProposta() == proposta.getIdProposta());
            clienteDao.aggiornaCliente(destinatario);

            // Trova tutti i conflitti (stesso libro offerto o richiesto)
            List<PropostaDiScambio> conflitti = new ArrayList<>();
            conflitti.addAll(propostaDao.cercaPropostaLibroOfferto(idOfferto));
            conflitti.addAll(propostaDao.cercaPropostaLibroRichiesto(idOfferto));
            conflitti.addAll(propostaDao.cercaPropostaLibroOfferto(idRichiesto));
            conflitti.addAll(propostaDao.cercaPropostaLibroRichiesto(idRichiesto));

            // Evita duplicati
            Set<Long> idsVisti = new HashSet<>();
            conflitti.removeIf(p -> !idsVisti.add(p.getIdProposta()));

            for (PropostaDiScambio p : conflitti) {
                if (p.getIdProposta() == proposta.getIdProposta()) continue; // salta la proposta principale
                if (p.getStato() != StatoProposta.IN_ATTESA) continue; // solo quelle ancora aperte

                // 1) Aggiorna stato nel DAO
                p.setStato(StatoProposta.RIFIUTATA);
                propostaDao.aggiungiRichiesta(p);

                // 2) Aggiorna mittente
                Utente m = (Utente) clienteDao.trovaPerUsername(p.getMittente().getUsername());
                m.getProposteInviate().stream()
                        .filter(px -> px.getIdProposta() == p.getIdProposta())
                        .findFirst()
                        .ifPresent(px -> px.setStato(StatoProposta.RIFIUTATA));
                clienteDao.aggiornaCliente(m);

                // 3) Aggiorna destinatario
                Utente d = (Utente) clienteDao.trovaPerUsername(p.getDestinatario().getUsername());
                d.getProposteRicevute().removeIf(px -> px.getIdProposta() == p.getIdProposta());
                clienteDao.aggiornaCliente(d);
            }
        }
        // opzionale: se il DAO mantiene indici sulla disponibilità, puoi anche rimuoverle fisicamente:
            // propostaDao.rimuoviRichiesta(p.getIdProposta()) al posto di setStato+upsert,
            // ma allora ricordati di allineare le liste dei clienti come sopra.
        }
    }

