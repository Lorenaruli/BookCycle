package it.uniroma2.eu.bookcycle.model.dao.file;

import java.io.*;


import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.dao.LIbroVenNolDao;
import it.uniroma2.eu.bookcycle.model.dao.LibroScambioDao;
import it.uniroma2.eu.bookcycle.model.domain.Libro;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LibroVenNolDaoFile extends LibroDaoFile implements LIbroVenNolDao {
    private static final String PROPERTIES_PATH = "proprieta.properties";



        public LibroVenNolDaoFile() throws DaoException {
            super("LIBRI_VENDITA_PATH");
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
            File file = inizializzaPercorsoDaProperties("LIBRI_VENDITA_PATH");
            if (!file.exists() || file.length() == 0) {
                this.libri = new ArrayList<>();
                return;
            }
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                this.libri = (List<Libro>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new DaoException("Errore durante il caricamento dei libri di vendita e noleggio");
            }
        }

        public void salvaLibri() throws DaoException {
            File file = inizializzaPercorsoDaProperties("LIBRI_VENDITA_PATH");
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(libri);
            } catch (IOException e) {
                throw new DaoException("Errore durante il salvataggio dei libri di vendita e noleggio");
            }
        }
    }