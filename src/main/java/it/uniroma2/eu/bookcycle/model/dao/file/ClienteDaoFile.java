package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.dao.ClienteDao;
import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.domain.Cliente;
import it.uniroma2.eu.bookcycle.model.domain.Libraio;
import it.uniroma2.eu.bookcycle.model.domain.Utente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ClienteDaoFile implements ClienteDao {

    private static final String PROPERTIES_PATH = "data/proprieta.properties";
    private final File file;

    public ClienteDaoFile() throws DaoException {
        this.file = inizializzaPercorsoDaProperties();
    }

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
                parent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
                // Inizializza il file con una lista vuota
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                    oos.writeObject(new ArrayList<Cliente>());
                }
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
        List<Cliente> clienti = leggiClienti();
        clienti.add(new Utente(username));
        scriviClienti(clienti);
    }

    @Override
    public void aggiungiLibraio(String username, String password, String telefono, String email) throws DaoException {
        if (esisteCliente(username)) {
            throw new DaoException("Username già esistente: " + username);
        }
        List<Cliente> clienti = leggiClienti();
        clienti.add(new Libraio(username));
        scriviClienti(clienti);
    }

    @Override
    public boolean esisteCliente(String username) throws DaoException {
        for (Cliente cliente : leggiClienti()) {
            if (cliente.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public Cliente getCliente(String username) throws DaoException {
        for (Cliente cliente : leggiClienti()) {
            if (cliente.getUsername().equals(username)) {
                return cliente;
            }
        }
        throw new DaoException("Cliente non trovato: " + username);
    }



    private List<Cliente> leggiClienti() throws DaoException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Cliente>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new DaoException("Errore nella lettura del file clienti");
        }
    }

    private void scriviClienti(List<Cliente> clienti) throws DaoException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(clienti);
        } catch (IOException e) {
            throw new DaoException("Errore nella scrittura del file clienti");
        }
    }

    public class ClienteRecord implements Serializable {
        private static final long serialVersionUID = 1L;

        private final Cliente cliente;  // può essere Utente o Libraio
        private final String password;
        private final String telefono;
        private final String email;

        public ClienteRecord(Cliente cliente, String password, String telefono, String email) {
            this.cliente = cliente;
            this.password = password;
            this.telefono = telefono;
            this.email = email;
        }

        public Cliente getCliente() {
            return cliente;
        }

        public String getPassword() {
            return password;
        }

        public String getTelefono() {
            return telefono;
        }

        public String getEmail() {
            return email;
        }
    }
}
