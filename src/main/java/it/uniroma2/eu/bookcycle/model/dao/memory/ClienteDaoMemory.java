package it.uniroma2.eu.bookcycle.model.dao.memory;

import it.uniroma2.eu.bookcycle.model.Eccezioni.ClienteNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.OggettoEsistenteException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.OggettoInvalidoException;
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


 public void aggiornaCliente(Cliente cliente) throws OggettoInvalidoException {
     if (cliente == null) {
         throw new OggettoInvalidoException("Cliente nullo");
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
    public void aggiungiUtente(String username, String password, String telefono, String email) throws OggettoEsistenteException {
        if (datiClienti.containsKey(username)) {
            throw new OggettoEsistenteException("Username già esistente: " + username);
        }
        Utente utente = new Utente(username);
        datiClienti.put(username, new DatiClienteM(utente, password, telefono, email));
        clienti.add(utente);
    }

    @Override
    public void aggiungiLibraio(String username, String password, String telefono, String email) throws OggettoEsistenteException {
        if (datiClienti.containsKey(username)) {
            throw new OggettoEsistenteException("Username già esistente: " + username);
        }
        Libraio libraio = new Libraio(username);
        datiClienti.put(username, new DatiClienteM(libraio, password, telefono, email));
        clienti.add(libraio);
    }

    @Override
    public String trovaEmail(String username) throws ClienteNonTrovatoException {
        DatiClienteM d = datiClienti.get(username);
        if (d == null) {
            throw new ClienteNonTrovatoException("Cliente con username " + username + " non trovato");
        }

        return d.getEmail();

    }

    @Override
    public String trovaTelefono(String username) throws ClienteNonTrovatoException {
        DatiClienteM d = datiClienti.get(username);
        if (d == null) {
            throw new ClienteNonTrovatoException("Cliente con username " + username + " non trovato");
        }

        return d.getTelefono();
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
    public Cliente ottieniCliente(String username) throws ClienteNonTrovatoException {
        DatiClienteM dati = datiClienti.get(username);
        if (dati == null) {
            throw new ClienteNonTrovatoException("Cliente non trovato: " + username);
        }
        return dati.getCliente();

    }
    @Override
    public Cliente trovaPerUsername(String username) throws ClienteNonTrovatoException {
        DatiClienteM dati = datiClienti.get(username);
        if (dati == null) {
            throw new ClienteNonTrovatoException("Cliente con username " + username + " non trovato");
        }
        return dati.getCliente();
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