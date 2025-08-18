package it.uniroma2.eu.bookcycle.model.dao.memory;

import it.uniroma2.eu.bookcycle.model.dao.ClienteDao;
import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.dao.file.ClienteDaoFile;
import it.uniroma2.eu.bookcycle.model.domain.Cliente;
import it.uniroma2.eu.bookcycle.model.domain.Libraio;
import it.uniroma2.eu.bookcycle.model.domain.Utente;

import java.util.*;

public class ClienteDaoMemory implements ClienteDao {
    private static ClienteDaoMemory instanza;
    private Map<String, DatiClienteM> datiClienti;
    private List<Cliente> clienti;

    private ClienteDaoMemory() {

        this.datiClienti = new HashMap<>();
        this.clienti = new ArrayList<>();
    }


 public void aggiornaCliente(Cliente cliente){
     if (cliente == null) {
         throw new DaoException("Cliente nullo");
     }

     for (int i = 0; i < clienti.size(); i++) {
         if (Objects.equals(clienti.get(i).getUsername(), cliente.getUsername())) {
             clienti.set(i, cliente);
             return;
         }
     }

 }



    public static ClienteDaoMemory ottieniIstanza(){
        if (instanza == null){
            instanza = new ClienteDaoMemory();

        }
        return instanza;
    }





    @Override
    public void aggiungiUtente(String username, String password, String telefono, String email) throws DaoException {
        if (datiClienti.containsKey(username)) {
            throw new DaoException("Username già esistente: " + username);
        }
        Utente utente = new Utente(username); // la tua entity
        datiClienti.put(username, new DatiClienteM(utente, password, telefono, email));
        clienti.add(utente);
    }

    @Override
    public void aggiungiLibraio(String username, String password, String telefono, String email) throws DaoException {
        if (datiClienti.containsKey(username)) {
            throw new DaoException("Username già esistente: " + username);
        }
        Libraio libraio = new Libraio(username); // la tua entity
        datiClienti.put(username, new DatiClienteM(libraio, password, telefono, email));
        clienti.add(libraio);
    }

    @Override
    public String trovaEmail(String username) {
        DatiClienteM d = datiClienti.get(username);
        return (d != null) ? d.getEmail() : null;
    }

    @Override
    public String trovaTelefono(String username) {
        DatiClienteM d = datiClienti.get(username);
        return (d != null) ? d.getTelefono() : null;
    }

    @Override
    public boolean esisteCliente(String username) {

        return datiClienti.containsKey(username);
    }

    @Override
    public boolean confrontaCredenziali(String username, String password) {
        DatiClienteM dati = datiClienti.get(username);
        return dati != null && dati.getPassword().equals(password);
    }



    public String getEmail(String username) throws DaoException {
        DatiClienteM dati = datiClienti.get(username);
        if (dati == null) {
            throw new DaoException("Cliente non trovato: " + username);
        }
        return dati.getEmail();
    }


    public String getTelefono(String username) throws DaoException {
        DatiClienteM dati = datiClienti.get(username);
        if (dati == null) {
            throw new DaoException("Cliente non trovato: " + username);
        }
        return dati.getTelefono();
    }

    @Override
    public Cliente ottieniCliente(String username) throws DaoException {
        DatiClienteM dati = datiClienti.get(username);
        if (dati == null) {
            throw new DaoException("Cliente non trovato: " + username);
        }
        return dati.getCliente();

    }
    @Override
    public Cliente trovaPerUsername(String username) {
        return clienti.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }


    private static class DatiClienteM {
        private final Cliente cliente;
        private final String password;
        private final String telefono;
        private final String email;


        public DatiClienteM(Cliente cliente, String password, String telefono, String email) {
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