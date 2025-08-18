package it.uniroma2.eu.bookcycle.controller;

import it.uniroma2.eu.bookcycle.bean.CaricaLibroBean;
import it.uniroma2.eu.bookcycle.model.dao.FactoryDao;
import it.uniroma2.eu.bookcycle.model.dao.LIbroVenNolDao;
import it.uniroma2.eu.bookcycle.model.dao.LibroScambioDao;
import it.uniroma2.eu.bookcycle.model.dao.file.LibroIdFacade;
import it.uniroma2.eu.bookcycle.model.domain.*;

public class CaricaLibroController {
    //private GestoreUtente gestoreUtente;
    private LibroScambioDao libroScambioDao;
    Cliente clienteAttuale=Sessione.ottieniIstanza().getClienteLoggato();

    public CaricaLibroController(){
        this.libroScambioDao = FactoryDao.getIstance().ottieniLibroScambioDao();
    }
    public CaricaLibroController(CaricaLibroBean bean){
        if (clienteAttuale == null){
            throw new RuntimeException("non ti sei loggato");
        }
    }


    public void AggiungiLibro(CaricaLibroBean bean) {
        Cliente clienteAttuale=Sessione.ottieniIstanza().getClienteLoggato();
        LIbroVenNolDao libroVenditaDao = FactoryDao.getIstance().ottieniLibroVeNolDao();

        LibroIdFacade libroId= new LibroIdFacade();
        long nuovoId = libroId.generaLibroId(libroScambioDao, libroVenditaDao);

        if (!bean.completo()) {
            throw new RuntimeException("non sono state fornite abbastanza informazioni");
        }
        Libro libro = new Libro(
                bean.getTitolo(),
                bean.getAutore(),
                bean.getGenere(),
                clienteAttuale.getUsername(),
                //DISPONIBILE,
                nuovoId
        );

        if (clienteAttuale instanceof Utente) {
            ((Utente) clienteAttuale).aggiungiLibro(libro);
            libroScambioDao.aggiungiLibro(libro);

            //Utente utente = (Utente) gestoreUtente.caricaLibriUtente(Sessione.ottieniIstanza().getClienteLoggato().getUsername());
        } else
            throw new RuntimeException("il cliente non è utente");
    }
    }

