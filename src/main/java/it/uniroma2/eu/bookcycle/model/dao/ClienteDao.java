package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.eccezioni.ClienteNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoEsistenteException;
import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.domain.Cliente;

import java.util.List;


public interface ClienteDao {
    List<Cliente> getTuttiClienti();
    void rimuoviCliente(String username) throws ClienteNonTrovatoException, PersistenzaException;
    void aggiungiLibraio(String username, String password, String telefono, String email) throws OggettoEsistenteException, PersistenzaException;
    void aggiungiUtente(String username, String password, String telefono, String email) throws OggettoEsistenteException, PersistenzaException;
    boolean esisteCliente(String username);
    Cliente ottieniCliente(String username) throws ClienteNonTrovatoException;
    boolean confrontaCredenziali(String username, String password);
    Cliente trovaPerUsername(String username) throws ClienteNonTrovatoException;
    void aggiornaCliente(Cliente cliente) throws OggettoInvalidoException, PersistenzaException;
    String trovaTelefono(String username) throws PersistenzaException, ClienteNonTrovatoException;
    String trovaEmail(String username) throws PersistenzaException, ClienteNonTrovatoException;

}
