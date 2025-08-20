package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.dao.LibroScambioDao;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LibroScambioDaoFile extends LibroDaoFile implements LibroScambioDao {
    private static final String PROPERTIES_PATH = "proprieta.properties";

    @Override
    public File inizializzaPercorsoDaProperties() throws DaoException {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream((PROPERTIES_PATH))) {
            if (input == null) {
                throw new DaoException("File di proprietà non trovato nel classpath");
            }

            Properties props = new Properties();
            props.load(input);
            String path = props.getProperty("LIBRI_SCAMBIO_PATH");
            if (path == null || path.isBlank()) {
                throw new DaoException("LIBRI_SCAMBIO_PATH non trovato nelle proprietà.");
            }

            File file = new File(path);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }

            return file;
        } catch (IOException e) {
            throw new DaoException("Errore nel caricamento del percorso dal file di proprietà");
        }
    }

}