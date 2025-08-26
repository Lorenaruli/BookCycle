package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.AnnuncioDaoComune;
import it.uniroma2.eu.bookcycle.model.domain.Annuncio;
import it.uniroma2.eu.bookcycle.model.domain.TipoAnnuncio;
import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.dao.AnnuncioDao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AnnuncioDaoFile extends AbstractFileDao  implements AnnuncioDao {
    private static final String PROPERTIES_PATH = "proprieta.properties";
    private static final String ANNUNCI_PATH = "ANNUNCI_PATH";
    private final File file;
    private List<Annuncio> annunci;
    private AnnuncioDaoComune helper;

    public AnnuncioDaoFile() throws PersistenzaException {
        this.file = inizializzaPercorsoDaProperties(ANNUNCI_PATH);
        this.annunci = caricaAnnunci();
        aggiornaIdCounter();
    }

    @Override
    protected void inizializzaFileVuoto(File file) throws PersistenzaException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(new ArrayList<Annuncio>());
        } catch (IOException _) {
            throw new PersistenzaException("Errore inizializzazione file annunci");
        }
    }


    private List<Annuncio> caricaAnnunci() throws PersistenzaException {
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<Annuncio>) obj;
            } else {
                throw new PersistenzaException("Formato file non valido");
            }
        } catch (IOException | ClassNotFoundException _) {
            return new ArrayList<>();
        }
    }

    private void salvaAnnunci() throws PersistenzaException {
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
            throw new PersistenzaException("Impossibile creare directory: " + parentDir.getAbsolutePath());

        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(annunci);
        } catch (IOException _) {
            throw new PersistenzaException("Errore scrittura annunci su " + file.getAbsolutePath());
        }
    }

    @Override
  public void aggiornaIdCounter() {
        helper.aggiornaIdCounter();
    }



    @Override
    public void salvaAnnuncio(Annuncio annuncio) throws OggettoInvalidoException, PersistenzaException {
        if (annuncio == null) throw new OggettoInvalidoException("Annuncio nullo");
        annunci.add(annuncio);

        salvaAnnunci();

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
      return helper.ottieniTuttiAnnunci();
    }


    @Override
    public List<Annuncio> cercaPerTitolo(String titolo) {
      return helper.cercaPerTitolo(titolo);
    }


    @Override
    public List<Annuncio> cercaPerAutore(String autore) {
      return helper.cercaPerAutore(autore);
    }

    @Override
    public List<Annuncio> cercaPerGenere(String genere) {
       return helper.cercaPerGenere(genere);
    }

    @Override
    public List<Annuncio> ottieniAnnunciPerTipo(TipoAnnuncio tipo) {
       return ottieniAnnunciPerTipo(tipo);
    }
}

