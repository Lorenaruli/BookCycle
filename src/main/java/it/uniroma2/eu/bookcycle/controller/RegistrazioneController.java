package it.uniroma2.eu.bookcycle.controller;

import it.uniroma2.eu.bookcycle.bean.RegistrazioneBean;
import it.uniroma2.eu.bookcycle.model.dao.ClienteDao;
import it.uniroma2.eu.bookcycle.model.dao.DaoException;

public class RegistrazioneController {
    private ClienteDao clienteDao;

    public RegistrazioneController(){
        this.clienteDao = null;
    }

    public void registra(RegistrazioneBean registrazioneBean){
        if (!registrazioneBean.completo()){
            throw new RuntimeException("non sono state abbastasnza informazioni");
        }
        boolean risultato;
        try {
            risultato = clienteDao.esisteCliente(registrazioneBean.getUsername());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        if (risultato){
            throw new RuntimeException("il nome utente esiste già");
        }
        try {
            switch (registrazioneBean.getRuolo()){
                case UTENTE -> clienteDao.aggiungiUtente(registrazioneBean.getUsername(),
                        registrazioneBean.getPassword(), registrazioneBean.getTelefono(),
                        registrazioneBean.getEmail());
                case LIBRAIO -> clienteDao.aggiungiLibraio(registrazioneBean.getUsername(),
                        registrazioneBean.getPassword(), registrazioneBean.getTelefono(),
                        registrazioneBean.getEmail());
            }
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }
}
