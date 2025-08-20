package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.domain.Cliente;
import it.uniroma2.eu.bookcycle.model.domain.Libro;

public interface ClienteDao {
    void aggiungiLibraio(String username, String password, String telefono, String email) throws DaoException;
    void aggiungiUtente(String username, String password, String telefono, String email) throws DaoException;
    boolean esisteCliente(String username) throws DaoException;
    Cliente ottieniCliente(String username);
    boolean confrontaCredenziali(String username, String password);
    Cliente trovaPerUsername(String username);
    void aggiornaCliente(Cliente cliente);
    String trovaTelefono(String username);
    String trovaEmail(String username);

}
