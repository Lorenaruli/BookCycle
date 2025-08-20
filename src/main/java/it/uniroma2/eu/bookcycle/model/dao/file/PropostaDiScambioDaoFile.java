package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.dao.PropostaDiScambioDao;
import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class PropostaDiScambioDaoFile implements PropostaDiScambioDao {

    private static final String PROPERTIES_PATH = "proprieta.properties";
    private final File file;
    private List<PropostaDiScambio> proposteTotali;

    public PropostaDiScambioDaoFile() throws DaoException {
        this.file = inizializzaPercorsoDaProperties();
        this.proposteTotali = caricaProposte();
        aggiornaIdCounter();
    }

    private File inizializzaPercorsoDaProperties() throws DaoException {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(PROPERTIES_PATH)) {
            Properties props = new Properties();
            props.load(input);
            String path = props.getProperty("SCAMBI_PATH");
            if (path == null || path.isBlank()) {
                throw new DaoException("SCAMBI_PATH non trovato nelle proprietà.");
            }
            File file = new File(path);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                    oos.writeObject(new ArrayList<PropostaDiScambio>());
                }
            }
            return file;
        } catch (IOException e) {
            throw new DaoException("Errore nel caricamento del percorso dal file di proprietà");
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
        return (max+1);
    }

    private List<PropostaDiScambio> caricaProposte() throws DaoException {
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<PropostaDiScambio>) obj;
            } else {
                throw new DaoException("Formato file non valido");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new DaoException("Errore lettura file: " + e.getMessage());
        }
    }

    private void salvaProposte() throws DaoException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(proposteTotali);
        } catch (IOException e) {
            throw new DaoException("Errore scrittura file: " + e.getMessage());
        }
    }

    @Override
    public void aggiungiProposta(PropostaDiScambio proposta) throws DaoException {
        if (proposta == null) {
            throw new DaoException("Proposta nulla");
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
    public void rimuoviProposta(long idProposta) throws DaoException {
        PropostaDiScambio daRimuovere = null;

        for (PropostaDiScambio p : proposteTotali) {
            if (p.getIdProposta() == idProposta) {
                daRimuovere = p;
                break;
            }
        }

        if (daRimuovere == null) {
            throw new DaoException("Proposta non trovata");
        }

        proposteTotali.remove(daRimuovere);
        salvaProposte();
    }

    @Override
    public List<PropostaDiScambio> getProposteRicevute(String usernameDestinatario) throws DaoException {
        return proposteTotali.stream()
                .filter(p -> p.getDestinatario().getUsername().equalsIgnoreCase(usernameDestinatario))
                .collect(Collectors.toList());
    }

    @Override
    public List<PropostaDiScambio> getProposteInviate(String usernameMittente) throws DaoException {
        return proposteTotali.stream()
                .filter(p -> p.getMittente().getUsername().equalsIgnoreCase(usernameMittente))
                .collect(Collectors.toList());
    }

    @Override
    public List<PropostaDiScambio> cercaPropostaLibroOfferto(long idLibro) {
        return proposteTotali.stream()
                .filter(p -> p.getLibroOfferto() != null
                        && p.getLibroOfferto().getIdLibro() == idLibro)
                .toList();

    }


    @Override
    public List<PropostaDiScambio> cercaPropostaLibroRichiesto(long idLibro) {
        return proposteTotali.stream()
                .filter(p -> p.getLibroRichiesto() != null
                        && p.getLibroRichiesto().getIdLibro() == idLibro)
                .toList();

    }

    @Override
    public PropostaDiScambio cercaPropostaId(long idProposta) {
        return proposteTotali.stream()
                .filter(p -> p.getIdProposta() == idProposta)
                .findFirst()
                .orElse(null);

    }
}

