package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.dao.LibroScambioDao;
import it.uniroma2.eu.bookcycle.model.domain.Libro;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class LibroScambioDaoFile extends LibroDaoFile implements LibroScambioDao {
    private static final String PROPERTIES_PATH = "proprieta.properties";


    public LibroScambioDaoFile() throws PersistenzaException {
        super("LIBRI_SCAMBIO_PATH");
    }

    @Override
    protected void inizializzaFileVuoto(File file) throws PersistenzaException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(new ArrayList<Libro>());
        } catch (IOException e) {
            throw new PersistenzaException("Errore inizializzazione file libri scambio");
        }


    }
}






