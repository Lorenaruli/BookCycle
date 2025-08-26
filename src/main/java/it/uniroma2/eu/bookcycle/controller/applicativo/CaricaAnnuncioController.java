package it.uniroma2.eu.bookcycle.controller.applicativo;

import it.uniroma2.eu.bookcycle.bean.CaricaAnnuncioBean;
import it.uniroma2.eu.bookcycle.model.eccezioni.ClienteNonLoggatoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.dao.*;
import it.uniroma2.eu.bookcycle.model.dao.file.LibroIdFacade;
import it.uniroma2.eu.bookcycle.model.domain.*;

public class CaricaAnnuncioController {

    private LIbroVenNolDao libroVen;
    private AnnuncioDao annuncioDao;
    Cliente clienteAttuale= Sessione.ottieniIstanza().getClienteLoggato();

    public CaricaAnnuncioController()throws ClienteNonLoggatoException, PersistenzaException{
        this.libroVen = FactoryDao.getIstance().ottieniLibroVeNolDao();
        this.annuncioDao = FactoryDao.getIstance().ottieniAnnuncioDao();
        if (clienteAttuale == null) {
            throw new ClienteNonLoggatoException("non ti sei loggato");
        }
    }


    public void aggiungiAnnuncio(CaricaAnnuncioBean bean) throws PersistenzaException, OggettoInvalidoException {

        LibroScambioDao libroScambio = FactoryDao.getIstance().ottieniLibroScambioDao();

        LibroIdFacade libroId= new LibroIdFacade();
        long nuovoId = libroId.generaLibroId(libroScambio, libroVen);

        Libro libro = new Libro(
                bean.getTitolo(),
                bean.getAutore(),
                clienteAttuale.getUsername(),
                nuovoId
        );
        if(bean.getTipo()==TipoAnnuncio.ANNUNCIOVENDITA) {
            Annuncio annuncio = new AnnuncioVendita(
                    libro,
                    bean.getPrezzo()

            );
            annuncioDao.salvaAnnuncio(annuncio);
        }
        if(bean.getTipo()==TipoAnnuncio.ANNUNCIONOLEGGIO) {
            Annuncio annuncio = new AnnuncioNoleggio(
                    libro,
                    bean.getPrezzo(),
                    bean.getDurata()
            );
            annuncioDao.salvaAnnuncio(annuncio);
        }

    }
        }


