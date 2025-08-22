package it.uniroma2.eu.bookcycle.controller;

import it.uniroma2.eu.bookcycle.bean.CaricaLibroBean;
import it.uniroma2.eu.bookcycle.model.Eccezioni.*;
import it.uniroma2.eu.bookcycle.model.dao.FactoryDao;
import it.uniroma2.eu.bookcycle.model.dao.LIbroVenNolDao;
import it.uniroma2.eu.bookcycle.model.dao.LibroScambioDao;
import it.uniroma2.eu.bookcycle.model.dao.file.LibroIdFacade;
import it.uniroma2.eu.bookcycle.model.domain.*;

public class CaricaLibroController {
    private LibroScambioDao libroScambioDao;
    Cliente clienteAttuale=Sessione.ottieniIstanza().getClienteLoggato();



    public CaricaLibroController() throws ClienteNonLoggatoException, PersistenzaException{
        if (clienteAttuale == null){
            throw new ClienteNonLoggatoException("non ti sei loggato");
        }
        this.libroScambioDao = FactoryDao.getIstance().ottieniLibroScambioDao();
    }


    public void AggiungiLibro(CaricaLibroBean bean) throws BeanInvalidoException, OggettoInvalidoException, PersistenzaException, RuoloClienteException {
        Cliente clienteAttuale=Sessione.ottieniIstanza().getClienteLoggato();
        LIbroVenNolDao libroVenditaDao = FactoryDao.getIstance().ottieniLibroVeNolDao();

        LibroIdFacade libroId= new LibroIdFacade();
        long nuovoId = libroId.generaLibroId(libroScambioDao, libroVenditaDao);

        if (!bean.completo()) {
            throw new BeanInvalidoException( "non sono state fornite abbastanza informazioni");
        }
        Libro libro = new Libro(
                bean.getTitolo(),
                bean.getAutore(),
                bean.getGenere(),
                clienteAttuale.getUsername(),
                nuovoId
        );

        if (clienteAttuale instanceof Utente) {
            ((Utente) clienteAttuale).aggiungiLibro(libro);
            libroScambioDao.aggiungiLibro(libro);

        } else
            throw new RuoloClienteException("il cliente non è utente");
    }
    }

