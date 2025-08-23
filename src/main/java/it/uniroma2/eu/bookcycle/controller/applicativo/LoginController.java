package it.uniroma2.eu.bookcycle.controller.applicativo;

import it.uniroma2.eu.bookcycle.bean.LoginBean;
import it.uniroma2.eu.bookcycle.model.eccezioni.BeanInvalidoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.ClienteNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.CredenzialiSbagliateException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.dao.ClienteDao;
import it.uniroma2.eu.bookcycle.model.dao.FactoryDao;
import it.uniroma2.eu.bookcycle.model.domain.Cliente;
import it.uniroma2.eu.bookcycle.model.domain.Libraio;
import it.uniroma2.eu.bookcycle.model.domain.RuoloCliente;
import it.uniroma2.eu.bookcycle.model.domain.Sessione;

public class LoginController {
    private ClienteDao clienteDao;

    public LoginController() throws PersistenzaException {
        this.clienteDao = FactoryDao.getIstance().ottieniClienteDao();
    }

    public RuoloCliente login(LoginBean loginBean) throws BeanInvalidoException, ClienteNonTrovatoException {
        if (!loginBean.completo()){
            throw new BeanInvalidoException("non sono state fornite abbastanza informazioni");
        }
        boolean risultato=clienteDao.confrontaCredenziali(loginBean.getUsername(), loginBean.getPassword());

        if (!risultato){
            throw new CredenzialiSbagliateException("credenziali sbagliate");
        }
        Cliente cliente = clienteDao.ottieniCliente(loginBean.getUsername());
        Sessione.ottieniIstanza().setClienteLoggato(cliente);
        if (cliente instanceof Libraio) {
            return RuoloCliente.LIBRAIO;
        } else {
            return RuoloCliente.UTENTE;
        }

    }
    public void logout() {
        Sessione.ottieniIstanza().setClienteLoggato(null);
    }


    }




