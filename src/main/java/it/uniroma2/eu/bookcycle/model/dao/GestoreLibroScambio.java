package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.domain.Libro;

public class GestoreLibroScambio {
        private LibroScambioDao libroDao;

        public GestoreLibroScambio() {
            this.libroDao = FactoryDao.getIstance().ottieniLibroScambioDao();
        }

        public Libro restituisciLibro(long id) {
            return libroDao.cercaPerId(id);
        }
    }

