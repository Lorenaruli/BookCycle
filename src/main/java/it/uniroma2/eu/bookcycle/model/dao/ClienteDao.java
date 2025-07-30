package it.uniroma2.eu.bookcycle.model.dao;

public interface ClienteDao {
    public void aggiungiLibraio(String username, String password, String telefono, String email) throws DaoException;
    public void aggiungiUtente(String username, String password, String telefono, String email) throws DaoException;
    public boolean esisteCliente(String username) throws DaoException;
}
