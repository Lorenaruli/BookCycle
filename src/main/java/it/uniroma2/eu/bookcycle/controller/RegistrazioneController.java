package it.uniroma2.eu.bookcycle.controller;

import it.uniroma2.eu.bookcycle.bean.ClienteBean;
import it.uniroma2.eu.bookcycle.bean.RegistrazioneBean;
import it.uniroma2.eu.bookcycle.model.dao.ClienteDao;
import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.dao.FactoryDao;
import it.uniroma2.eu.bookcycle.model.domain.*;

public class RegistrazioneController {
    private ClienteDao clienteDao;

    public RegistrazioneController() {
        this.clienteDao = FactoryDao.getIstance().ottieniClienteDao();
    }

    public ClienteBean registra(RegistrazioneBean registrazioneBean) throws BeanInvalidoException{
        if (!registrazioneBean.completo()) {
            throw new BeanInvalidoException("non sono state fornite abbastanza informazioni");
        }
        boolean risultato;
        try {
            risultato = clienteDao.esisteCliente(registrazioneBean.getUsername());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        if (risultato) {
            throw new RuntimeException("scegliere un altro nome utente");
        }
        try {
            switch (registrazioneBean.getRuolo()) {
                case UTENTE -> {
                    clienteDao.aggiungiUtente(registrazioneBean.getUsername(),
                            registrazioneBean.getPassword(), registrazioneBean.getTelefono(),
                            registrazioneBean.getEmail());
                    Utente nuovoUtente = new Utente(registrazioneBean.getUsername());
                    Sessione.ottieniIstanza().setClienteLoggato(nuovoUtente);
                    ClienteBean clienteBean= new ClienteBean();
                    clienteBean.setRuoloCliente(RuoloCliente.UTENTE);
                    clienteBean.setUsername(registrazioneBean.getUsername());

                    return clienteBean;
                }


                case LIBRAIO -> {
                    clienteDao.aggiungiLibraio(registrazioneBean.getUsername(),
                        registrazioneBean.getPassword(), registrazioneBean.getTelefono(),
                        registrazioneBean.getEmail());

                    Libraio nuovoLibraio = new Libraio(registrazioneBean.getUsername());
                    Sessione.ottieniIstanza().setClienteLoggato(nuovoLibraio);
                    ClienteBean clienteBean= new ClienteBean();
                    clienteBean.setRuoloCliente(RuoloCliente.LIBRAIO);
                    clienteBean.setUsername(registrazioneBean.getUsername());

                    return clienteBean;
                }
                default -> throw new RuntimeException("Ruolo non valido");

            }
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

    }
}






