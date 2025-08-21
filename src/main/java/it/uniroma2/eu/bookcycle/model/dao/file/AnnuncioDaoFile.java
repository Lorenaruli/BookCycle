package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.Eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.dao.AnnuncioDao;
import it.uniroma2.eu.bookcycle.model.domain.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AnnuncioDaoFile extends AbstractFileDao  implements AnnuncioDao  {
    private static final String PROPERTIES_PATH = "proprieta.properties";
    private static final String ANNUNCI_PATH = "ANNUNCI_PATH";
    private final File file;
    private List<Annuncio> annunci;

    public AnnuncioDaoFile() throws PersistenzaException  {
        this.file = inizializzaPercorsoDaProperties(ANNUNCI_PATH);
        this.annunci = caricaAnnunci();
        aggiornaIdCounter();
    }

    @Override
    protected void inizializzaFileVuoto(File file) throws PersistenzaException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                    oos.writeObject(new ArrayList<Annuncio>());
                }
    catch (IOException e) {
        throw new PersistenzaException("Errore inizializzazione file annunci");
     }
    }



    private List<Annuncio> caricaAnnunci() throws PersistenzaException {
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<Annuncio>) obj;
            } else {
                throw new PersistenzaException("Formato file non valido");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenzaException("Errore lettura annunci: ");
        }
    }

    private void salvaAnnunci() throws PersistenzaException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(annunci);
        } catch (IOException e) {
            throw new PersistenzaException("Errore scrittura annunci: " );
        }
    }

    @Override
    public void aggiornaIdCounter() {
        long max = 0;
        for (Annuncio a : annunci) {
            if (a.getIdAnnuncio() > max) {
                max = a.getIdAnnuncio();
            }
        }
        Annuncio.setIdCounter(max + 1);
    }

    @Override
    public void salvaAnnuncio(Annuncio annuncio) throws OggettoInvalidoException, PersistenzaException {
        if (annuncio == null) throw new OggettoInvalidoException("Annuncio nullo");
        annunci.add(annuncio);

            salvaAnnunci();

    }

    @Override
    public List<Annuncio> cercaPerProprietario(String username) {
        return annunci.stream()
                .filter(a -> a.getLibraio().getUsername().equalsIgnoreCase(username))
                .collect(Collectors.toList());
    }

    @Override
    public void rimuoviAnnuncio(long idAnnuncio) throws OggettoInvalidoException, PersistenzaException {
        Annuncio daRimuovere = null;

        for (Annuncio a : annunci) {
            if (a.getIdAnnuncio() == idAnnuncio) {
                daRimuovere = a;
                break;
            }
        }

        if (daRimuovere == null) {
            throw new OggettoInvalidoException("Annuncio non trovato");
        }

        annunci.remove(daRimuovere);
        salvaAnnunci();
    }

    @Override
    public List<Annuncio> ottieniTuttiAnnunci() {
        return new ArrayList<>(annunci);
    }

    @Override
    public List<Annuncio> ottieniAnnunciPerLibraio(String usernameLibraio) {
        if (usernameLibraio == null) {
            return Collections.emptyList();
        }
        return annunci.stream()
                .filter(a -> a.getLibraio().getUsername().equalsIgnoreCase(usernameLibraio))
                .collect(Collectors.toList());
    }

    @Override
    public List<Annuncio> cercaPerTitolo(String titolo) {
        if (titolo == null)  return Collections.emptyList();
        return annunci.stream()
                .filter(a -> a.getLibro().getTitolo().toLowerCase().contains(titolo.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Annuncio> cercaPerAutore(String autore)  {
        if (autore == null) return Collections.emptyList();
        return annunci.stream()
                .filter(a -> a.getLibro().getAutore().toLowerCase().contains(autore.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Annuncio> cercaPerGenere(String genere)  {
        if (genere == null) return Collections.emptyList();
        return annunci.stream()
                .filter(a -> a.getLibro().getGenere().toLowerCase().contains(genere.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Annuncio> ottieniAnnunciPerTipo(TipoAnnuncio tipo)  {
        if (tipo == null) return Collections.emptyList();
        return annunci.stream()
                .filter(a -> a.getTipo() == tipo)
                .collect(Collectors.toList());
    }
}

