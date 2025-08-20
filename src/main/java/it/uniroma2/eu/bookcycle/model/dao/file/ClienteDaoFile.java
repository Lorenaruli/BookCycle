package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.dao.ClienteDao;
import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.domain.Cliente;
import it.uniroma2.eu.bookcycle.model.domain.Libraio;
import it.uniroma2.eu.bookcycle.model.domain.Utente;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ClienteDaoFile implements ClienteDao {

    private List<Cliente> clienti;
    private static final String PROPERTIES_PATH = "proprieta.properties";
    private File file;
    private Map<String, DatiClienteF> datiClienti;

    public ClienteDaoFile() throws DaoException {
        this.file = inizializzaPercorsoDaProperties();
        this.datiClienti = new HashMap<>();
        leggiDatiClienti();
    }

    private File inizializzaPercorsoDaProperties() throws DaoException {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(PROPERTIES_PATH)) {
            if (input == null) {
                throw new DaoException("File di proprietà non trovato nel classpath");
            }

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
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                    oos.writeObject(new ArrayList<DatiClienteF>());
                }
            }

            return file;
        } catch (IOException e) {
            throw new DaoException("Errore nel caricamento del percorso dal file di proprietà");
        }
    }

    private void caricaDati(List<DatiClienteF> lista) {
        datiClienti.clear();
        for (DatiClienteF dati : lista) {
            datiClienti.put(dati.getCliente().getUsername(), dati);
        }
    }

    public void aggiornaCliente(Cliente cliente) {
        if (cliente == null) {
            throw new DaoException("Cliente nullo");
        }

        for (int i = 0; i < clienti.size(); i++) {
            if (Objects.equals(clienti.get(i).getUsername(), cliente.getUsername())) {
                clienti.set(i, cliente);
                salvaClienti();
                return;
            }
        }
    }




        private List<DatiClienteF> leggiDatiClienti() throws DaoException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<DatiClienteF> lista = (List<DatiClienteF>) ois.readObject();
            caricaDati(lista);
            this.clienti = lista.stream()
                    .map(DatiClienteF::getCliente)
                    .collect(Collectors.toList());  // <- AGGIUNGI QUESTO
            return lista;
        } catch (IOException | ClassNotFoundException e) {
            throw new DaoException("Errore nella lettura del file clienti");
        }
    }

    private void salvaClienti() throws DaoException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(new ArrayList<>(datiClienti.values()));
        } catch (IOException e) {
            throw new DaoException("Errore scrittura clienti");
        }
    }

    @Override
    public void aggiungiUtente(String username, String password, String telefono, String email) throws DaoException {
        if (esisteCliente(username)) {
            throw new DaoException("Username già esistente: " + username);
        }
        List<DatiClienteF> clienti = leggiDatiClienti();
        Cliente utente = new Utente(username);
        DatiClienteF dati = new DatiClienteF(utente, password, telefono, email);
        clienti.add(dati);
        datiClienti.put(username, dati);
        salvaClienti();
    }
    @Override
    public String trovaEmail(String username) {
        DatiClienteF d = datiClienti.get(username);
        if (d == null) {
            try { leggiDatiClienti(); } catch (DaoException e) { e.printStackTrace(); return null; }
            d = datiClienti.get(username);
        }
        return (d != null) ? d.getEmail() : null;
    }

    @Override
    public String trovaTelefono(String username) {
        DatiClienteF d = datiClienti.get(username);
        if (d == null) {
            try { leggiDatiClienti(); } catch (DaoException e) { e.printStackTrace(); return null; }
            d = datiClienti.get(username);
        }
        return (d != null) ? d.getTelefono() : null;
    }


    @Override
    public void aggiungiLibraio(String username, String password, String telefono, String email) throws DaoException {
        if (esisteCliente(username)) {
            throw new DaoException("Username già esistente: " + username);
        }
        List<DatiClienteF> clienti = leggiDatiClienti();
        Cliente libraio = new Libraio(username);
        clienti.add(new DatiClienteF(libraio, password, telefono, email));
        salvaClienti();
    }
    @Override
    public Cliente trovaPerUsername(String username) {
        DatiClienteF dati = datiClienti.get(username);
        return (dati != null) ? dati.getCliente() : null;
    }

    @Override
    public boolean esisteCliente(String username) {
        return datiClienti.containsKey(username);
    }

    @Override
    public Cliente ottieniCliente(String username) throws DaoException {
        DatiClienteF dati = datiClienti.get(username);
        if (dati == null) {
            throw new DaoException("Cliente non trovato: " + username);
        }
        return dati.getCliente();
    }

    @Override
    public boolean confrontaCredenziali(String username, String password) {
        DatiClienteF dati = datiClienti.get(username);
        return dati != null && Objects.equals(dati.getPassword(), password);
    }

    public static class DatiClienteF implements Serializable {

        private Cliente cliente;
        private String password;
        private String telefono;
        private String email;

        public DatiClienteF(Cliente cliente, String password, String telefono, String email) {
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