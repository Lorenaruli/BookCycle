package it.uniroma2.eu.bookcycle.controller.applicativo;

import it.uniroma2.eu.bookcycle.bean.CaricaAnnuncioBean;
import it.uniroma2.eu.bookcycle.model.Eccezioni.ClienteNonLoggatoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.RuoloClienteException;
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


    public void AggiungiAnnuncio(CaricaAnnuncioBean bean) throws PersistenzaException, RuoloClienteException, OggettoInvalidoException {

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

    }
        }


