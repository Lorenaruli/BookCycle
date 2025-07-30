package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.dao.AnnuncioDao;
import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.domain.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AnnuncioDaoFile implements AnnuncioDao {

    private static final String FILE_PATH = "data/annunci.dat";
    private List<Annuncio> annunci;

    public AnnuncioDaoFile() throws DaoException {
        this.annunci = caricaAnnunci();
    }

    private List<Annuncio> caricaAnnunci() throws DaoException {
        File file = new File(FILE_PATH);
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
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(annunci);
        } catch (IOException e) {
            throw new DaoException("Errore scrittura annunci: " + e.getMessage());
        }
    }

    @Override
    public void salvaAnnuncio(Annuncio annuncio) throws DaoException {
        if (annuncio == null) throw new DaoException("Annuncio nullo");
        annunci.add(annuncio);
        salvaSuFile();
    }

    @Override
    public void rimuoviAnnuncio(Annuncio annuncio) throws DaoException {
        if (!annunci.remove(annuncio)) {
            throw new DaoException("Annuncio non trovato");
        }
        salvaSuFile();
    }

    @Override
    public List<Annuncio> getTuttiAnnunci() throws DaoException {
        return new ArrayList<>(annunci);
    }

    @Override
    public List<Annuncio> getAnnunciPerLibraio(String usernameLibraio) throws DaoException {
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
    public List<Annuncio> getAnnunciPerTipo(TipoAnnuncio tipo) throws DaoException {
        if (tipo == null) throw new DaoException("Tipo nullo");
        return annunci.stream()
                .filter(a -> a.getTipo() == tipo)
                .collect(Collectors.toList());
    }
}
