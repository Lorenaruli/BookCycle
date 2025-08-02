package it.uniroma2.eu.bookcycle.model.dao.memory;

import it.uniroma2.eu.bookcycle.model.dao.ClienteDao;
import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.domain.Cliente;
import it.uniroma2.eu.bookcycle.model.domain.Libraio;
import it.uniroma2.eu.bookcycle.model.domain.Utente;

import java.util.HashMap;
import java.util.Map;

public class ClienteDaoMemory implements ClienteDao {
    private static ClienteDaoMemory instanza;


    public static ClienteDaoMemory ottieniIstanza(){
        if (instanza == null){
            instanza = new ClienteDaoMemory();

        }
        return instanza;
    }

    // Mappa principale: username → DatiCliente
    private final Map<String, DatiCliente> datiClienti;

    public ClienteDaoMemory() {
        this.datiClienti = new HashMap<>();
    }

    @Override
    public void aggiungiUtente(String username, String password, String telefono, String email) throws DaoException {
        if (datiClienti.containsKey(username)) {
            throw new DaoException("Username già esistente: " + username);
        }
        Utente utente = new Utente(username); // la tua entity
        datiClienti.put(username, new DatiCliente(utente, password, telefono, email));
    }

    @Override
    public void aggiungiLibraio(String username, String password, String telefono, String email) throws DaoException {
        if (datiClienti.containsKey(username)) {
            throw new DaoException("Username già esistente: " + username);
        }
        Libraio libraio = new Libraio(username); // la tua entity
        datiClienti.put(username, new DatiCliente(libraio, password, telefono, email));
    }

    @Override
    public boolean esisteCliente(String username) {
        return datiClienti.containsKey(username);
    }

    @Override
    public boolean confrontaCredenziali(String username, String password) {
        DatiCliente dati = datiClienti.get(username);
        return dati != null && dati.getPassword().equals(password);
    }


//    Metodo per recuperare la mail
//    public String getEmail(String username) throws DaoException {
//        DatiCliente dati = datiClienti.get(username);
//        if (dati == null) {
//            throw new DaoException("Cliente non trovato: " + username);
//        }
//        return dati.getEmail();
//    }

    // Metodo per recuperare il numero di telefono
    public String getTelefono(String username) throws DaoException {
        DatiCliente dati = datiClienti.get(username);
        if (dati == null) {
            throw new DaoException("Cliente non trovato: " + username);
        }
        return dati.getTelefono();
    }

    @Override
    public Cliente ottieniCliente(String username) throws DaoException {
        DatiCliente dati = datiClienti.get(username);
        if (dati == null) {
            throw new DaoException("Cliente non trovato: " + username);
        }
        return dati.getCliente();
    }

    // Classe interna per contenere i dati aggiuntivi (non presenti nelle entity)
    private static class DatiCliente {
        private final Cliente cliente;
        private final String password;
        private final String telefono;
        private final String email;


        public DatiCliente(Cliente cliente, String password, String telefono, String email) {
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