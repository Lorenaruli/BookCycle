package it.uniroma2.eu.bookcycle.controller;

import it.uniroma2.eu.bookcycle.bean.LoginBean;
import it.uniroma2.eu.bookcycle.model.dao.ClienteDao;
import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.dao.FactoryDao;
import it.uniroma2.eu.bookcycle.model.domain.Cliente;
import it.uniroma2.eu.bookcycle.model.domain.Libraio;
import it.uniroma2.eu.bookcycle.model.domain.RuoloCliente;
import it.uniroma2.eu.bookcycle.model.domain.Sessione;

public class LoginController {
    private ClienteDao clienteDao;

    public LoginController(){
        this.clienteDao = FactoryDao.getIstance().ottieniClienteDao();
    }
    public RuoloCliente login(LoginBean loginBean) throws RuntimeException{
        if (!loginBean.completo()){
            throw new RuntimeException("non sono state fornite abbastanza informazioni");
        }

            boolean risultato=clienteDao.confrontaCredenziali(loginBean.getUsername(), loginBean.getPassword());
            if (!risultato){
                throw new RuntimeException("credenziali sbagliate");
            }
        try {
            Cliente cliente = clienteDao.ottieniCliente(loginBean.getUsername());
            Sessione.ottieniIstanza().setClienteLoggato(cliente);
            if (cliente instanceof Libraio) {
                return RuoloCliente.LIBRAIO;
            } else {
                return RuoloCliente.UTENTE;
            }

        } catch (DaoException e) {
            throw new RuntimeException("Errore durante il recupero dell'utente");
        }
    }
    public void logout() {
        Sessione.ottieniIstanza().setClienteLoggato(null);
    }


    }




