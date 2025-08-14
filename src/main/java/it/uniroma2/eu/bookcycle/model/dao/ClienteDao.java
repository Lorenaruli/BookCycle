package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.domain.Cliente;
import it.uniroma2.eu.bookcycle.model.domain.Libro;

public interface ClienteDao {
    public void aggiungiLibraio(String username, String password, String telefono, String email) throws DaoException;
    public void aggiungiUtente(String username, String password, String telefono, String email) throws DaoException;
    public boolean esisteCliente(String username) throws DaoException;
    public Cliente ottieniCliente(String username);
    public boolean confrontaCredenziali(String username, String password);
    public Cliente trovaPerUsername(String username);
    public void aggiornaCliente(Cliente cliente);
    public String trovaTelefono(String username);
    public String trovaEmail(String username);

}
