package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.dao.ClienteDao;
import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.domain.Cliente;
import it.uniroma2.eu.bookcycle.model.domain.Libraio;
import it.uniroma2.eu.bookcycle.model.domain.Utente;

import java.io.*;
import java.util.Properties;

public class ClienteDaoFile implements ClienteDao {

    private static final String PROPERTIES_PATH = "data/proprieta.properties";
    private final File file;

    public ClienteDaoFile() throws DaoException {
        this.file = inizializzaPercorsoDaProperties();
    }

    // Metodo per ottenere il percorso del file da proprieta.properties
    private File inizializzaPercorsoDaProperties() throws DaoException {
        try (InputStream input = new FileInputStream(PROPERTIES_PATH)) {
            Properties props = new Properties();
            props.load(input);
            String path = props.getProperty("CLIENTI_PATH");
            if (path == null || path.isBlank()) {
                throw new DaoException("CLIENTI_PATH non trovato nelle proprietà.");
            }
            File file = new File(path);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs(); // crea directory se non esiste
            }
            if (!file.exists()) {
                file.createNewFile(); // crea file se non esiste
            }
            return file;
        } catch (IOException e) {
            throw new DaoException("Errore nel caricamento del percorso dal file di proprietà");
        }
    }

    @Override
    public void aggiungiUtente(String username, String password, String telefono, String email) throws DaoException {
        if (esisteCliente(username)) {
            throw new DaoException("Username già esistente: " + username);
        }
        scriviSuFile("UTENTE;" + username + ";" + password + ";" + telefono + ";" + email);
    }

    @Override
    public void aggiungiLibraio(String username, String password, String telefono, String email) throws DaoException {
        if (esisteCliente(username)) {
            throw new DaoException("Username già esistente: " + username);
        }
        scriviSuFile("LIBRAIO;" + username + ";" + password + ";" + telefono + ";" + email);
    }

    @Override
    public boolean esisteCliente(String username) throws DaoException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String riga;
            while ((riga = reader.readLine()) != null) {
                String[] campi = riga.split(";");
                if (campi.length >= 2 && campi[1].equals(username)) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            throw new DaoException("Errore nella lettura del file clienti");
        }
    }

    // Metodo per login: controlla username e password
    public boolean verificaCredenziali(String username, String password) throws DaoException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String riga;
            while ((riga = reader.readLine()) != null) {
                String[] campi = riga.split(";");
                if (campi.length >= 3 && campi[1].equals(username) && campi[2].equals(password)) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            throw new DaoException("Errore nella verifica delle credenziali");
        }
    }

    // Metodo per recuperare la entity Cliente associata
    public Cliente getCliente(String username) throws DaoException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String riga;
            while ((riga = reader.readLine()) != null) {
                String[] campi = riga.split(";");
                if (campi.length >= 2 && campi[1].equals(username)) {
                    String tipo = campi[0];
                    return switch (tipo) {
                        case "UTENTE" -> new Utente(username);
                        case "LIBRAIO" -> new Libraio(username);
                        default -> throw new DaoException("Tipo di cliente sconosciuto: " + tipo);
                    };
                }
            }
            throw new DaoException("Cliente non trovato: " + username);
        } catch (IOException e) {
            throw new DaoException("Errore nella lettura del file clienti");
        }
    }

    // Metodo per scrivere una nuova riga nel file
    private void scriviSuFile(String riga) throws DaoException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(riga);
            writer.newLine();
        } catch (IOException e) {
            throw new DaoException("Errore nella scrittura del file clienti");
        }
    }

    // Metodi aggiuntivi per leggere telefono o email
    public String getEmail(String username) throws DaoException {
        return leggiCampo(username, 4);
    }

    public String getTelefono(String username) throws DaoException {
        return leggiCampo(username, 3);
    }

    private String leggiCampo(String username, int indiceCampo) throws DaoException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String riga;
            while ((riga = reader.readLine()) != null) {
                String[] campi = riga.split(";");
                if (campi.length > indiceCampo && campi[1].equals(username)) {
                    return campi[indiceCampo];
                }
            }
            throw new DaoException("Cliente non trovato: " + username);
        } catch (IOException e) {
            throw new DaoException("Errore nella lettura del file");
        }
    }
}