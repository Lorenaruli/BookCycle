package it.uniroma2.eu.bookcycle.controller.applicativo;

import it.uniroma2.eu.bookcycle.bean.ClienteBean;
import it.uniroma2.eu.bookcycle.bean.RegistrazioneBean;
import it.uniroma2.eu.bookcycle.model.Eccezioni.BeanInvalidoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.OggettoEsistenteException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.dao.ClienteDao;
import it.uniroma2.eu.bookcycle.model.dao.FactoryDao;
import it.uniroma2.eu.bookcycle.model.domain.*;

public class RegistrazioneController {
    private ClienteDao clienteDao;

    public RegistrazioneController() throws PersistenzaException{
        this.clienteDao = FactoryDao.getIstance().ottieniClienteDao();
    }

    public ClienteBean registra(RegistrazioneBean registrazioneBean) throws BeanInvalidoException, OggettoEsistenteException, PersistenzaException {
        if (!registrazioneBean.completo()) {
            throw new BeanInvalidoException("non sono state fornite abbastanza informazioni");
        }
        boolean risultato;
        if (clienteDao.esisteCliente(registrazioneBean.getUsername())) {
            throw new OggettoEsistenteException("Username già registrato: " + registrazioneBean.getUsername());
        }

        switch (registrazioneBean.getRuolo()) {
            case UTENTE -> {
                clienteDao.aggiungiUtente(registrazioneBean.getUsername(),
                        registrazioneBean.getPassword(), registrazioneBean.getTelefono(),
                        registrazioneBean.getEmail());
                Utente nuovoUtente = new Utente(registrazioneBean.getUsername());
                Sessione.ottieniIstanza().setClienteLoggato(nuovoUtente);
                ClienteBean clienteBean = new ClienteBean();
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
                ClienteBean clienteBean = new ClienteBean();
                clienteBean.setRuoloCliente(RuoloCliente.LIBRAIO);
                clienteBean.setUsername(registrazioneBean.getUsername());

                return clienteBean;
            }

            default -> throw new IllegalArgumentException("Ruolo non valido: " + registrazioneBean.getRuolo());
        }
    }
}






