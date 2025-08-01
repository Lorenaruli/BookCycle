package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.dao.LibroDao;
import it.uniroma2.eu.bookcycle.model.domain.Libro;
import it.uniroma2.eu.bookcycle.model.domain.StatoLibro;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public abstract class LibroDaoFile implements LibroDao {
        private File file;
        private List<Libro> libri;


        public LibroDaoFile() throws DaoException {
            this.file = inizializzaPercorsoDaProperties();
        }

        public abstract File inizializzaPercorsoDaProperties() throws DaoException;

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
        public List<Libro> getLibri() throws DaoException {
            // implementazione solo se SCAMBIABILI sono riconoscibili tramite proprietà
            return libri.stream()
                    .filter(l -> l.getStato() == StatoLibro.DISPONIBILE)
                    .collect(Collectors.toList());
        }

    public void caricaLibri() throws DaoException {
        File file = inizializzaPercorsoDaProperties();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            this.libri = (List<Libro>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new DaoException("Errore durante il caricamento dei libri di scambio");
        }
    }
    @Override
    public void rimuoviLibro(long idLibro) throws DaoException {
        boolean rimosso = libri.removeIf(libro -> libro.getIdLibro() == idLibro);

        if (!rimosso) {
            throw new DaoException("Nessun libro trovato con ID " + idLibro);
        }

        salvaLibri(); // salva la lista aggiornata nel file
    }

    public void salvaLibri() throws DaoException {
        File file = inizializzaPercorsoDaProperties();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(libri);
        } catch (IOException e) {
            throw new DaoException("Errore durante il salvataggio dei libri di scambio");
        }
    }
    }


