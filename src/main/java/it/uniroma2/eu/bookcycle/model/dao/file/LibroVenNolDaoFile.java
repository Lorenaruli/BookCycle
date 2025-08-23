package it.uniroma2.eu.bookcycle.model.dao.file;

import java.io.*;


import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.dao.LIbroVenNolDao;
import it.uniroma2.eu.bookcycle.model.domain.Libro;

import java.util.ArrayList;

public class LibroVenNolDaoFile extends LibroDaoFile implements LIbroVenNolDao {
    private static final String PROPERTIES_PATH = "proprieta.properties";



        public LibroVenNolDaoFile() throws PersistenzaException {
            super("LIBRI_VENDITA_PATH");
        }

        @Override
        protected void inizializzaFileVuoto(File file) throws PersistenzaException {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(new ArrayList<Libro>());
            }
         catch (IOException _) {
        throw new PersistenzaException("Errore inizializzazione file libri scambio");
    }
        }


    }