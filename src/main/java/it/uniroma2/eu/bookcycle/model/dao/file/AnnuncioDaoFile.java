package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.dao.AnnuncioDao;
import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.domain.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class AnnuncioDaoFile implements AnnuncioDao {

    private final File file;
    private List<Annuncio> annunci;

    public AnnuncioDaoFile() throws DaoException {
        this.file = inizializzaPercorsoDaProperties();
        this.annunci = caricaAnnunci();
        aggiornaIdCounterDaFile();
    }

    private File inizializzaPercorsoDaProperties() throws DaoException {
        try (InputStream input = getClass().getResourceAsStream("proprieta.properties")) {
            Properties props = new Properties();
            props.load(input);
            String path = props.getProperty("ANNUNCI_PATH");
            if (path == null || path.isBlank()) {
                throw new DaoException("ANNUNCI_PATH non trovato nelle proprietà.");
            }
            File file = new File(path);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
                // Inizializza con lista vuota
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                    oos.writeObject(new ArrayList<Annuncio>());
                }
            }
            return file;
        } catch (IOException e) {
            throw new DaoException("Errore nel caricamento del percorso dal file di proprietà");
        }
    }

    private List<Annuncio> caricaAnnunci() throws DaoException {
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<Annuncio>) obj;
            } else {
                throw new DaoException("Formato file non valido");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new DaoException("Errore lettura annunci: " + e.getMessage());
        }
    }

    private void salvaSuFile() throws DaoException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(annunci);
        } catch (IOException e) {
            throw new DaoException("Errore scrittura annunci: " + e.getMessage());
        }
    }

    @Override
    public void aggiornaIdCounterDaFile() {
        long max = 0;
        for (Annuncio a : annunci) {
            if (a.getIdAnnuncio() > max) {
                max = a.getIdAnnuncio();
            }
        }
        Annuncio.setIdCounter(max + 1);
    }

    @Override
    public void salvaAnnuncio(Annuncio annuncio) throws DaoException {
        if (annuncio == null) throw new DaoException("Annuncio nullo");
        annunci.add(annuncio);
        salvaSuFile();
    }

    @Override
    public void rimuoviAnnuncio(long idAnnuncio) throws DaoException {
        Annuncio daRimuovere = null;

        for (Annuncio a : annunci) {
            if (a.getIdAnnuncio() == idAnnuncio) {
                daRimuovere = a;
                break;
            }
        }

        if (daRimuovere == null) {
            throw new DaoException("Annuncio non trovato");
        }

        annunci.remove(daRimuovere);
        salvaSuFile();
    }

    @Override
    public List<Annuncio> ottieniTuttiAnnunci() throws DaoException {
        return new ArrayList<>(annunci);
    }

    @Override
    public List<Annuncio> ottieniAnnunciPerLibraio(String usernameLibraio) throws DaoException {
        if (usernameLibraio == null) throw new DaoException("Username nullo");
        return annunci.stream()
                .filter(a -> a.getLibraio().getUsername().equalsIgnoreCase(usernameLibraio))
                .collect(Collectors.toList());
    }

    @Override
    public List<Annuncio> cercaPerTitolo(String titolo) throws DaoException {
        if (titolo == null) throw new DaoException("Titolo nullo");
        return annunci.stream()
                .filter(a -> a.getLibro().getTitolo().toLowerCase().contains(titolo.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Annuncio> cercaPerAutore(String autore) throws DaoException {
        if (autore == null) throw new DaoException("Autore nullo");
        return annunci.stream()
                .filter(a -> a.getLibro().getAutore().toLowerCase().contains(autore.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Annuncio> cercaPerGenere(String genere) throws DaoException {
        if (genere == null) throw new DaoException("Genere nullo");
        return annunci.stream()
                .filter(a -> a.getLibro().getGenere().toLowerCase().contains(genere.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Annuncio> ottieniAnnunciPerTipo(TipoAnnuncio tipo) throws DaoException {
        if (tipo == null) throw new DaoException("Tipo nullo");
        return annunci.stream()
                .filter(a -> a.getTipo() == tipo)
                .collect(Collectors.toList());
    }
}

