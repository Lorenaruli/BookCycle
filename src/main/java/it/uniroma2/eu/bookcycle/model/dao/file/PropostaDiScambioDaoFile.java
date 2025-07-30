package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.dao.PropostaDiScambioDao;
import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PropostaDiScambioDaoFile implements PropostaDiScambioDao {

    private static final String FILE_PATH = "data/proposte_scambio.dat";
    private List<PropostaDiScambio> proposte;

    public PropostaDiScambioDaoFile() throws DaoException {
        this.proposte = caricaProposte();
    }

    private List<PropostaDiScambio> caricaProposte() throws DaoException {
        File file = new File(FILE_PATH);
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

    private void salvaSuFile() throws DaoException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(proposte);
        } catch (IOException e) {
            throw new DaoException("Errore scrittura file: " + e.getMessage());
        }
    }

    @Override
    public void salvaRichiesta(PropostaDiScambio proposta) throws DaoException {
        if (proposta == null) throw new DaoException("Proposta nulla");
        proposte.add(proposta);
        salvaSuFile();
    }

    @Override
    public void rimuoviRichiesta(PropostaDiScambio proposta) throws DaoException {
        if (!proposte.remove(proposta)) {
            throw new DaoException("Proposta non trovata");
        }
        salvaSuFile();
    }

    @Override
    public List<PropostaDiScambio> getRichiesteRicevute(String usernameDestinatario) throws DaoException {
        return proposte.stream()
                .filter(p -> p.getDestinatario().getUsername().equalsIgnoreCase(usernameDestinatario))
                .collect(Collectors.toList());
    }

    @Override
    public List<PropostaDiScambio> getRichiesteInviate(String usernameMittente) throws DaoException {
        return proposte.stream()
                .filter(p -> p.getMittente().getUsername().equalsIgnoreCase(usernameMittente))
                .collect(Collectors.toList());
    }

    @Override
    public List<PropostaDiScambio> getTutteRichieste() throws DaoException {
        return new ArrayList<>(proposte);
    }
}
