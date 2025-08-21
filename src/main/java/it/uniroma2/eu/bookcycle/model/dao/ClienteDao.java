package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.Eccezioni.ClienteNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.OggettoEsistenteException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.domain.Cliente;


public interface ClienteDao {
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
