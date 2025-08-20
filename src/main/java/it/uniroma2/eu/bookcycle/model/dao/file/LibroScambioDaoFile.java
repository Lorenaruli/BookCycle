package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.dao.LibroScambioDao;
import it.uniroma2.eu.bookcycle.model.domain.Libro;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class LibroScambioDaoFile extends LibroDaoFile implements LibroScambioDao {
    private static final String PROPERTIES_PATH = "proprieta.properties";


    public LibroScambioDaoFile() throws DaoException {
        super("LIBRI_SCAMBIO_PATH");
        caricaLibri();
    }

    @Override
    protected void inizializzaFileVuoto(File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(new ArrayList<Libro>());
        }
    }

    @Override
    public void caricaLibri() throws DaoException {
        File file = inizializzaPercorsoDaProperties("LIBRI_SCAMBIO_PATH");
        if (!file.exists() || file.length() == 0) {
            this.libri = new ArrayList<>();
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            this.libri = (List<Libro>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new DaoException("Errore durante il caricamento dei libri di scambio");
        }
    }

    public void salvaLibri() throws DaoException {
        File file = inizializzaPercorsoDaProperties("LIBRI_SCAMBIO_PATH");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(libri);
        } catch (IOException e) {
            throw new DaoException("Errore durante il salvataggio dei libri di scambio");
        }
    }
}




//   @Override
//    public File inizializzaPercorsoDaProperties() throws DaoException {
//        try (InputStream input = getClass().getClassLoader().getResourceAsStream((PROPERTIES_PATH))) {
//            if (input == null) {
//                throw new DaoException("File di proprietà non trovato nel classpath");
//            }
//
//            Properties props = new Properties();
//            props.load(input);
//            String path = props.getProperty("LIBRI_SCAMBIO_PATH");
//            if (path == null || path.isBlank()) {
//                throw new DaoException("LIBRI_SCAMBIO_PATH non trovato nelle proprietà.");
//            }
//
//            File file = new File(path);
//            File parent = file.getParentFile();
//            if (parent != null && !parent.exists()) {
//                parent.mkdirs();
//            }
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//
//            return file;
//        } catch (IOException e) {
//            throw new DaoException("Errore nel caricamento del percorso dal file di proprietà");
//        }
//    }

