package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.domain.Cliente;

public interface ClienteDao {
    public void aggiungiLibraio(String username, String password, String telefono, String email) throws DaoException;
    public void aggiungiUtente(String username, String password, String telefono, String email) throws DaoException;
    public boolean esisteCliente(String username) throws DaoException;
    public Cliente ottieniCliente(String username);
    public boolean confrontaCredenziali(String username, String password);
    //aggiungi metodo per ottenere la lista di libri associati a un proprietario qui

}
