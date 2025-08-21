package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.dao.DaoException;


import java.io.*;

import java.util.Properties;


public abstract class AbstractFileDao {

    private static final String PROPERTIES_PATH = "proprieta.properties";

    protected File inizializzaPercorsoDaProperties(String key) throws PersistenzaException {
        Properties props = new Properties();

        try (InputStream input = AbstractFileDao.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH)) {
            if (input == null) {
                throw new DaoException("File di proprietà non trovato nel classpath: " + PROPERTIES_PATH);
            }

            props.load(input);
            String path = props.getProperty(key);

            if (path == null || path.isBlank()) {
                throw new DaoException(key + " non trovato nelle proprietà.");
            }

            File file = new File(path);

            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                if (!parent.mkdirs() && !parent.exists()) {
                    throw new DaoException("Impossibile creare la directory: " + parent.getAbsolutePath());
                }
            }

            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new DaoException("Impossibile creare il file: " + file.getAbsolutePath());
                }
                inizializzaFileVuoto(file);
            }

            return file;

        } catch (IOException e) {
            throw new DaoException("Errore nel caricamento del percorso dal file di proprietà");
        }
    }

    protected abstract void inizializzaFileVuoto(File file) throws PersistenzaException;
}