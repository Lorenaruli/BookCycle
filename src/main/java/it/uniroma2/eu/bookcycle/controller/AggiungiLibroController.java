package it.uniroma2.eu.bookcycle.controller;

import it.uniroma2.eu.bookcycle.model.domain.Sessione;

public class AggiungiLibroController {

    public AggiungiLibroController(){
        if (Sessione.ottieniIstanza().getClienteLoggato() == null){
            throw new RuntimeException("non ti sei loggato");
        }
    }
}
