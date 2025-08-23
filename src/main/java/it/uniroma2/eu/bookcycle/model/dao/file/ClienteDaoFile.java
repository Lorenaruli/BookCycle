package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.eccezioni.ClienteNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoEsistenteException;
import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.dao.ClienteDao;
import it.uniroma2.eu.bookcycle.model.domain.Cliente;
import it.uniroma2.eu.bookcycle.model.domain.Libraio;
import it.uniroma2.eu.bookcycle.model.domain.Utente;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ClienteDaoFile extends AbstractFileDao implements ClienteDao {

    private List<Cliente> clienti;
    private static final String CLIENTI_PATH = "CLIENTI_PATH";
    private File file;
    private Map<String, DatiClienteF> datiClienti;

    public ClienteDaoFile() throws PersistenzaException {
        this.file = inizializzaPercorsoDaProperties(CLIENTI_PATH);
        this.datiClienti = new HashMap<>();
        leggiClienti();
    }

    @Override
    protected void inizializzaFileVuoto(File file) throws PersistenzaException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(new ArrayList<DatiClienteF>());}
            catch (IOException _) {
                throw new PersistenzaException("Errore inizializzazione file clienti");

        }
    }



    private void caricaDati(List<DatiClienteF> lista) {
        datiClienti.clear();
        for (DatiClienteF dati : lista) {
            datiClienti.put(dati.getCliente().getUsername(), dati);
        }
    }

    public void aggiornaCliente(Cliente cliente) throws OggettoInvalidoException, PersistenzaException{
        if (cliente == null) {
            throw new OggettoInvalidoException("Cliente nullo");
        }

        for (int i = 0; i < clienti.size(); i++) {
            if (Objects.equals(clienti.get(i).getUsername(), cliente.getUsername())) {
                clienti.set(i, cliente);
                salvaClienti();
                return;
            }
        }
    }




    private List<DatiClienteF> leggiClienti() throws PersistenzaException {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
        List<DatiClienteF> lista = (List<DatiClienteF>) ois.readObject();
        caricaDati(lista);
        this.clienti = lista.stream()
                .map(DatiClienteF::getCliente)
                .collect(Collectors.toList());
        return lista;
    } catch (IOException | ClassNotFoundException _) {
        throw new PersistenzaException("Errore nella lettura del file clienti");
    }
}

    private void salvaClienti() throws PersistenzaException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(new ArrayList<>(datiClienti.values()));
        } catch (IOException _) {
            throw new PersistenzaException("Errore scrittura clienti");
        }
    }

    @Override
    public void aggiungiLibraio(String username, String password, String telefono, String email) throws OggettoEsistenteException, PersistenzaException {
        if (esisteCliente(username)) {
            throw new OggettoEsistenteException("Username già esistente: " + username);
        }
        List<DatiClienteF> lista = leggiClienti();
        Cliente libraio = new Libraio(username);
        DatiClienteF dati = new DatiClienteF(libraio, password, telefono, email);
        lista.add(dati);
        this.datiClienti.put(username,dati);
        salvaClienti();
    }

    @Override
    public void aggiungiUtente(String username, String password, String telefono, String email) throws OggettoEsistenteException, PersistenzaException {
        if (esisteCliente(username)) {
            throw new OggettoEsistenteException("Username già esistente: " + username);
        }
        List<DatiClienteF> lista = leggiClienti();
        Cliente utente = new Utente(username);
        DatiClienteF dati = new DatiClienteF(utente, password, telefono, email);
        lista.add(dati);
        this.datiClienti.put(username, dati);
        salvaClienti();
    }

    @Override
    public void rimuoviCliente(String username) throws ClienteNonTrovatoException, PersistenzaException {
        Cliente daRimuovere = null;

        for (Cliente c : clienti) {
            if (c.getUsername().equals(username)) {
                daRimuovere = c;
                break;
            }
        }

        if (daRimuovere == null) {
            throw new ClienteNonTrovatoException("Cliente non trovata");
        }

        clienti.remove(daRimuovere);
        salvaClienti();
    }

    @Override
    public String trovaEmail(String username) throws PersistenzaException, ClienteNonTrovatoException {
        DatiClienteF d = datiClienti.get(username);
        if (d == null) {
            leggiClienti();
            d = datiClienti.get(username);
        }
        if (d == null) {
            throw new ClienteNonTrovatoException("Cliente con username " + username + " non trovato");
        }

        return d.getEmail();
    }

    @Override
    public String trovaTelefono(String username) throws PersistenzaException, ClienteNonTrovatoException {
        DatiClienteF d = datiClienti.get(username);
        if (d == null) {
            leggiClienti();
            d = datiClienti.get(username);
        }
        if (d == null) {
            throw new ClienteNonTrovatoException("Cliente con username " + username + " non trovato");
        }

        return d.getTelefono();
    }



    @Override
    public Cliente trovaPerUsername(String username) throws ClienteNonTrovatoException {
        DatiClienteF dati = datiClienti.get(username);
        if (dati == null) {
            throw new ClienteNonTrovatoException("Cliente con username " + username + " non trovato");
        }
        return dati.getCliente();
    }

    @Override
    public boolean esisteCliente(String username) {
        return datiClienti.containsKey(username);
    }

    @Override
    public Cliente ottieniCliente(String username) throws ClienteNonTrovatoException {
        DatiClienteF dati = datiClienti.get(username);
        if (dati == null) {
            throw new ClienteNonTrovatoException("Cliente non trovato: " + username);
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