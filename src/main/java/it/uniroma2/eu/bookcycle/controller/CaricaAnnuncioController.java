package it.uniroma2.eu.bookcycle.controller;

import it.uniroma2.eu.bookcycle.bean.CaricaAnnuncioBean;
import it.uniroma2.eu.bookcycle.model.dao.*;
import it.uniroma2.eu.bookcycle.model.dao.file.LibroIdFacade;
import it.uniroma2.eu.bookcycle.model.domain.*;

public class CaricaAnnuncioController {

    private LIbroVenNolDao libroVen;
    private AnnuncioDao annuncioDao;
    Cliente clienteAttuale= Sessione.ottieniIstanza().getClienteLoggato();

    public CaricaAnnuncioController(){
        this.libroVen = FactoryDao.getIstance().ottieniLibroVeNolDao();
        this.annuncioDao =FactoryDao.getIstance().ottieniAnnuncioDao();
    }
    public CaricaAnnuncioController(CaricaAnnuncioBean bean){
        if (clienteAttuale == null){
            throw new RuntimeException("non ti sei loggato");
        }
    }

    public void AggiungiAnnuncio(CaricaAnnuncioBean bean){

        Cliente clienteAttuale=Sessione.ottieniIstanza().getClienteLoggato();
        LibroScambioDao libroScambio = FactoryDao.getIstance().ottieniLibroScambioDao();

        LibroIdFacade libroId= new LibroIdFacade();
        long nuovoId = libroId.generaLibroId(libroScambio, libroVen);

        Libro libro = new Libro(
                bean.getTitolo(),
                bean.getAutore(),
                clienteAttuale.getUsername(),
                nuovoId
        );
        GestoreLibraio gestore=new GestoreLibraio();
        if(bean.getTipo()==TipoAnnuncio.ANNUNCIOVENDITA) {
            Annuncio annuncio = new AnnuncioVendita(
                    libro,
                    bean.getPrezzo(),
                    gestore.restituisciLibraio(clienteAttuale.getUsername())

            );
            annuncioDao.salvaAnnuncio(annuncio);
        }
        if(bean.getTipo()==TipoAnnuncio.ANNUNCIONOLEGGIO) {
            Annuncio annuncio = new AnnuncioNoleggio(
                    libro,
                    bean.getPrezzo(),
                    gestore.restituisciLibraio(clienteAttuale.getUsername()),
                    bean.getDurata()
            );
            annuncioDao.salvaAnnuncio(annuncio);
        }


        else
            throw new RuntimeException("il cliente non è libraio");
    }
        }


