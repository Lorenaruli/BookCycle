package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.Eccezioni.LibroNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.domain.Libro;

public class GestoreLibroScambio {
        private LibroScambioDao libroDao;

        public GestoreLibroScambio() throws PersistenzaException {
            this.libroDao = FactoryDao.getIstance().ottieniLibroScambioDao();
        }

        public Libro restituisciLibro(long id) throws LibroNonTrovatoException {
            return libroDao.cercaPerId(id);
        }
    }

