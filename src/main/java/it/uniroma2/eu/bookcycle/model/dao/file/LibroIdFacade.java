package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.dao.LIbroVenNolDao;
import it.uniroma2.eu.bookcycle.model.dao.LibroScambioDao;
import it.uniroma2.eu.bookcycle.model.domain.Libro;

import java.util.List;

public class LibroIdFacade {
    public long generaLibroId(LibroScambioDao scambioDao, LIbroVenNolDao venditaDao) throws DaoException {
        long max = 0;

        List<Libro> libriScambio = scambioDao.getTuttiLibri();
        List<Libro> libriVendita = venditaDao.getTuttiLibri();

        for (Libro l : libriScambio) {
            if (l.getIdLibro() > max) {
                max = l.getIdLibro();
            }
        }

        for (Libro l : libriVendita) {
            if (l.getIdLibro() > max) {
                max = l.getIdLibro();
            }
        }
        return max + 1;
    }
}
