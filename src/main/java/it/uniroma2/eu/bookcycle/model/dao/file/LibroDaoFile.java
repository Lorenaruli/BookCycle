package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.eccezioni.LibroNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.dao.LibroDao;
import it.uniroma2.eu.bookcycle.model.domain.Libro;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class LibroDaoFile extends AbstractFileDao implements LibroDao {
        private File file;
        protected List<Libro> libri;
        protected String propertiesKey;



    protected LibroDaoFile(String propertiesKey) throws PersistenzaException {
        this.file = inizializzaPercorsoDaProperties(propertiesKey);
        this.propertiesKey=propertiesKey;

        if (this.file.length() == 0) {
            try {
                inizializzaFileVuoto(file);
            } catch (PersistenzaException _) {
                throw new PersistenzaException("Errore inizializzazione file " + file.getAbsolutePath());
            }
        }
        caricaLibri();
    }
    protected abstract void inizializzaFileVuoto(File file) throws PersistenzaException;



    @Override
    public List<Libro> cercaPerTitolo(String titolo) {
        List<Libro> risultati = new ArrayList<>();
        for (Libro l : libri) {
            if (l.getTitolo().equalsIgnoreCase(titolo)) {
                risultati.add(l);
            }
        }
        return risultati;
    }

    @Override
    public List<Libro> cercaPerAutore(String autore) {
        List<Libro> risultati = new ArrayList<>();
        for (Libro l : libri) {
            if (l.getAutore().equalsIgnoreCase(autore)) {
                risultati.add(l);
            }
        }
        return risultati;
    }

    @Override
    public List<Libro> cercaPerGenere(String genere) {
        List<Libro> risultati = new ArrayList<>();
        for (Libro l : libri) {
            if (l.getGenere().equalsIgnoreCase(genere)) {
                risultati.add(l);
            }
        }
        return risultati;
    }


@Override
public List<Libro> getTuttiLibri() {
    return new ArrayList<>(libri);

}


public void caricaLibri() throws PersistenzaException {
    if (!file.exists() || file.length() == 0) {
        this.libri = new ArrayList<>();
        return;
    }
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
        this.libri = (List<Libro>) ois.readObject();
    } catch (IOException | ClassNotFoundException _) {
        throw new PersistenzaException("Errore durante il caricamento dei libri ");
    }
}

public void salvaLibri() throws PersistenzaException {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
        oos.writeObject(libri);
    } catch (IOException _) {
        throw new PersistenzaException("Errore durante il salvataggio dei libri");
    }
}



@Override
public void rimuoviLibro(long idLibro) throws PersistenzaException, LibroNonTrovatoException {
    boolean rimosso = libri.removeIf(libro -> libro.getIdLibro() == idLibro);

    if (!rimosso) {
        throw new LibroNonTrovatoException("Nessun libro trovato con ID " + idLibro);
    }

    salvaLibri();
}
    @Override
    public void aggiungiLibro(Libro libro) throws PersistenzaException, OggettoInvalidoException {
        if (libri == null) {
            caricaLibri();
        }
        if (libro == null) throw new OggettoInvalidoException("Libro nullo");
        libri.add(libro);
        salvaLibri();

    }


    @Override
    public Libro cercaPerId(long id) throws LibroNonTrovatoException {
        for (Libro l : libri) {
            if (l.getIdLibro() == id) {
                return l;
            }
        }
        throw new LibroNonTrovatoException("Nessun libro trovato con id: " + id);
    }


}



