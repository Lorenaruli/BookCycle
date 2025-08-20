package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.dao.LibroDao;
import it.uniroma2.eu.bookcycle.model.domain.Libro;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class LibroDaoFile extends AbstractFileDao implements LibroDao {
        private File file;
        protected List<Libro> libri;
        protected String propertiesKey;



    protected LibroDaoFile(String propertiesKey) throws DaoException {
        this.file = inizializzaPercorsoDaProperties(propertiesKey);
        this.propertiesKey=propertiesKey;

        if (this.file.length() == 0) {
            try {
                inizializzaFileVuoto(file);
            } catch (IOException e) {
                throw new DaoException("Errore inizializzazione file " + file.getAbsolutePath());
            }
        }
        caricaLibri(propertiesKey);
    }
    protected abstract void inizializzaFileVuoto(File file) throws IOException;


//        public LibroDaoFile() throws DaoException {
//            this.file = inizializzaPercorsoDaProperties();
//            caricaLibri();
//        }

//       public abstract File inizializzaPercorsoDaProperties(String key) throws DaoException;

        @Override
        public List<Libro> cercaPerTitolo(String titolo) throws DaoException {
            return libri.stream()
                    .filter(l -> l.getTitolo().equalsIgnoreCase(titolo))
                    .collect(Collectors.toList());
        }

        @Override
        public List<Libro> cercaPerAutore(String autore) throws DaoException {
            return libri.stream()
                    .filter(l -> l.getAutore().equalsIgnoreCase(autore))
                    .collect(Collectors.toList());
        }

        @Override
        public List<Libro> cercaPerGenere(String genere) throws DaoException {
            return libri.stream()
                    .filter(l -> l.getGenere().equalsIgnoreCase(genere))
                    .collect(Collectors.toList());
        }

    @Override
    public List<Libro> cercaPerProprietario(String username) throws DaoException {
        return libri.stream()
                .filter(l -> l.getUsernameProprietario().equalsIgnoreCase(username))
                .collect(Collectors.toList());
    }


    @Override
    public List<Libro> getTuttiLibri() throws DaoException {
        return new ArrayList<>(libri);

    }


    public void caricaLibri(String propertiesKey) throws DaoException {
        File file = inizializzaPercorsoDaProperties(propertiesKey);
        if (!file.exists() || file.length() == 0) {
            this.libri = new ArrayList<>();
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            this.libri = (List<Libro>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new DaoException("Errore durante il caricamento dei libri ");
        }
    }

    public void salvaLibri(String propertiesKey) throws DaoException {
        File file = inizializzaPercorsoDaProperties(propertiesKey);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(libri);
        } catch (IOException e) {
            throw new DaoException("Errore durante il salvataggio dei libri di vendita e noleggio");
        }
    }



//    public void caricaLibri() throws DaoException {
//        File file = inizializzaPercorsoDaProperties();
//        if (!file.exists() || file.length() == 0) {
//            this.libri = new ArrayList<>();
//            return;
//        }
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
//            this.libri = (List<Libro>) ois.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            throw new DaoException("Errore durante il caricamento dei libri di scambio");
//        }
//    }
    @Override
    public void rimuoviLibro(long idLibro) throws DaoException {
        boolean rimosso = libri.removeIf(libro -> libro.getIdLibro() == idLibro);

        if (!rimosso) {
            throw new DaoException("Nessun libro trovato con ID " + idLibro);
        }

        salvaLibri(propertiesKey);
    }
    @Override
    public void aggiungiLibro(Libro libro) throws DaoException {
        if (libri == null) {
            caricaLibri(propertiesKey);
        }

        libri.add(libro);
        salvaLibri(propertiesKey);
    }


//    public void salvaLibri() throws DaoException {
//        File file = inizializzaPercorsoDaProperties();
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
//            oos.writeObject(libri);
//        } catch (IOException e) {
//            throw new DaoException("Errore durante il salvataggio dei libri di scambio");
//        }
//    }
    @Override
    public Libro cercaPerId(long id) {
        return libri.stream()
                .filter(l -> l.getIdLibro() == id)
                .findFirst()
                .orElse(null);
    }

    }



