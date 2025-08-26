package it.uniroma2.eu.bookcycle.controller.applicativo;

import it.uniroma2.eu.bookcycle.bean.CaricaLibroBean;
import it.uniroma2.eu.bookcycle.model.dao.*;
import it.uniroma2.eu.bookcycle.model.eccezioni.*;
import it.uniroma2.eu.bookcycle.model.dao.file.LibroIdFacade;
import it.uniroma2.eu.bookcycle.model.domain.*;

public class CaricaLibroController {
    private LibroScambioDao libroScambioDao;
    private ClienteDao clienteDao;
    Cliente clienteAttuale=Sessione.ottieniIstanza().getClienteLoggato();



    public CaricaLibroController() throws ClienteNonLoggatoException, PersistenzaException{
        if (clienteAttuale == null){
            throw new ClienteNonLoggatoException("non ti sei loggato");
        }
        this.libroScambioDao = FactoryDao.getIstance().ottieniLibroScambioDao();
        this.clienteDao=FactoryDao.getIstance().ottieniClienteDao();
    }


    public void aggiungiLibro(CaricaLibroBean bean) throws BeanInvalidoException, OggettoInvalidoException, PersistenzaException, RuoloClienteException {
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
                nuovoId
        );

        if (clienteAttuale instanceof Utente ) {

            Utente utenteLoggato=(Utente) clienteDao.ottieniCliente(clienteAttuale.getUsername());
            utenteLoggato.aggiungiLibro(libro);
            clienteDao.aggiornaCliente(utenteLoggato);
            libroScambioDao.aggiungiLibro(libro);

        } else
            throw new RuoloClienteException("il cliente non è utente");
    }
    }

