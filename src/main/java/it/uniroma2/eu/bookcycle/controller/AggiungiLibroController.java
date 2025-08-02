package it.uniroma2.eu.bookcycle.controller;

import it.uniroma2.eu.bookcycle.bean.AggiungiLibroBean;
import it.uniroma2.eu.bookcycle.model.dao.LibroDao;
import it.uniroma2.eu.bookcycle.model.domain.*;

public class AggiungiLibroController {
    private AggiungiLibroBean aggiungiLibroBean;

    public AggiungiLibroController(){
        this.aggiungiLibroBean= aggiungiLibroBean;
        if (Sessione.ottieniIstanza().getClienteLoggato() == null){
            throw new RuntimeException("non ti sei loggato");
        }
    }
    private LibroDao libroDao;
    public void AggiungiLibro(AggiungiLibroBean aggiungiLibroBean){
        if (!aggiungiLibroBean.completo()){
            throw new RuntimeException("non sono state fornite abbastanza informazioni");
        }
        Libro libro=new Libro( aggiungiLibroBean.getTitolo(),aggiungiLibroBean.getAutore(), aggiungiLibroBean.getGenere());
        if (Sessione.ottieniIstanza().getClienteLoggato() instanceof Utente) {
            ((Utente)Sessione.ottieniIstanza().getClienteLoggato()).aggiungiLibro(libro);
             libroDao.aggiungiLibro(libro);}
        else
            throw new RuntimeException("il cliente non è utente");



    }


}
