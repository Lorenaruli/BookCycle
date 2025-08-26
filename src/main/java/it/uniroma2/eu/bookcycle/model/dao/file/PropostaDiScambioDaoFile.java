package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PropostaNonTrovataException;
import it.uniroma2.eu.bookcycle.model.dao.PropostaDiScambioDao;
import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PropostaDiScambioDaoFile extends AbstractFileDao implements PropostaDiScambioDao {
    private static final String SCAMBI_PATH = "SCAMBI_PATH";
    private final File file;
    private List<PropostaDiScambio> proposteTotali;

    public PropostaDiScambioDaoFile() throws PersistenzaException {
        this.file = inizializzaPercorsoDaProperties(SCAMBI_PATH);
        this.proposteTotali = caricaProposte();
        aggiornaIdCounter();
    }


    @Override
    protected void inizializzaFileVuoto(File file) throws PersistenzaException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(new ArrayList<PropostaDiScambio>());
        } catch (IOException _) {
            throw new PersistenzaException("Errore inizializzazione file clienti");

        }
    }


    @Override
    public long aggiornaIdCounter() {
        long max = 0;
        for (PropostaDiScambio p : proposteTotali) {
            if (p.getIdProposta() > max) {
                max = p.getIdProposta();
            }
        }
        return (max + 1);
    }

    private List<PropostaDiScambio> caricaProposte() throws PersistenzaException {
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<PropostaDiScambio>) obj;
            } else {
                throw new PersistenzaException("Formato file non valido");
            }
        } catch (IOException | ClassNotFoundException _) {
            throw new PersistenzaException("Errore lettura file.");
        }
    }

    private void salvaProposte() throws PersistenzaException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(proposteTotali);
        } catch (IOException _) {
            throw new PersistenzaException("Errore scrittura file.");
        }
    }

    @Override
    public void aggiungiProposta(PropostaDiScambio proposta) throws OggettoInvalidoException, PersistenzaException {
        if (proposta == null) {
            throw new OggettoInvalidoException("Proposta nulla");
        }
        for (int i = 0; i < proposteTotali.size(); i++) {
            if (proposteTotali.get(i).getIdProposta() == proposta.getIdProposta()) {
                proposteTotali.set(i, proposta);
                salvaProposte();

                return;
            }
        }
        proposteTotali.add(proposta);
        salvaProposte();
    }


    @Override
    public void rimuoviProposta(long idProposta) throws PropostaNonTrovataException, PersistenzaException {
        PropostaDiScambio daRimuovere = null;

        for (PropostaDiScambio p : proposteTotali) {
            if (p.getIdProposta() == idProposta) {
                daRimuovere = p;
                break;
            }
        }

        if (daRimuovere == null) {
            throw new PropostaNonTrovataException("Proposta non trovata");
        }

        proposteTotali.remove(daRimuovere);
        salvaProposte();
    }


    @Override
    public List<PropostaDiScambio> cercaPropostaLibroOfferto(long idLibro) {
        List<PropostaDiScambio> risultati = new ArrayList<>();
        for (PropostaDiScambio p : proposteTotali) {
            if (p.getLibroOfferto() != null && p.getLibroOfferto().getIdLibro() == idLibro) {
                risultati.add(p);
            }
        }
        return risultati;
    }

    @Override
    public List<PropostaDiScambio> cercaPropostaLibroRichiesto(long idLibro) {
        List<PropostaDiScambio> risultati = new ArrayList<>();
        for (PropostaDiScambio p : proposteTotali) {
            if (p.getLibroRichiesto() != null && p.getLibroRichiesto().getIdLibro() == idLibro) {
                risultati.add(p);
            }
        }
        return risultati;
    }

    @Override
    public PropostaDiScambio cercaPropostaId(long idProposta) throws PropostaNonTrovataException {
        for (PropostaDiScambio p : proposteTotali) {
            if (p.getIdProposta() == idProposta) {
                return p;
            }
        }
        throw new PropostaNonTrovataException("Nessuna proposta trovata con id: " + idProposta);
    }
}


