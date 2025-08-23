package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.dao.LibroScambioDao;
import it.uniroma2.eu.bookcycle.model.domain.Libro;

import java.io.*;
import java.util.ArrayList;


public class LibroScambioDaoFile extends LibroDaoFile implements LibroScambioDao {
    private static final String PROPERTIES_PATH = "proprieta.properties";


    public LibroScambioDaoFile() throws PersistenzaException {
        super("LIBRI_SCAMBIO_PATH");
    }

    @Override
    protected void inizializzaFileVuoto(File file) throws PersistenzaException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(new ArrayList<Libro>());
        } catch (IOException _) {
            throw new PersistenzaException("Errore inizializzazione file libri scambio");
        }


    }
}






