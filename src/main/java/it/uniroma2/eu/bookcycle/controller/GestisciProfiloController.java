package it.uniroma2.eu.bookcycle.controller;

import it.uniroma2.eu.bookcycle.bean.GestisciProfiloBean;
import it.uniroma2.eu.bookcycle.model.dao.LibroDao;
import it.uniroma2.eu.bookcycle.model.dao.GestoreUtente;
import it.uniroma2.eu.bookcycle.model.domain.*;

import java.util.List;

public class GestisciProfiloController {
    private GestoreUtente gestoreUtente;
    private LibroDao libroDao;
    Cliente clienteAttuale=Sessione.ottieniIstanza().getClienteLoggato();

    public GestisciProfiloController(GestisciProfiloBean bean){
        if (clienteAttuale == null){
            throw new RuntimeException("non ti sei loggato");
        }
    }


    public void AggiungiLibro(GestisciProfiloBean bean){
        if (!bean.completo()){
            throw new RuntimeException("non sono state fornite abbastanza informazioni");
        }
        Libro libro = new Libro(
                bean.getTitolo(),
                bean.getAutore(),
                bean.getGenere(),
                clienteAttuale.getUsername()
        );


        if (clienteAttuale instanceof Utente) {
            ((Utente)Sessione.ottieniIstanza().getClienteLoggato()).aggiungiLibro(libro);
             libroDao.aggiungiLibro(libro);

             Utente utente= (Utente) gestoreUtente.caricaLibriUtente(Sessione.ottieniIstanza().getClienteLoggato().getUsername());}
        else
            throw new RuntimeException("il cliente non è utente");
    }
//    public void SalvaLibriProfilo() {
//        List<Libro> libriUtente = libroDao.cercaPerProprietario(clienteAttuale.getUsername());
//        } non so come salvare
    }

