package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PropostaNonTrovataException;
import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;

public class GestoreProposta {
    private static final GestoreProposta instance =new GestoreProposta();

    private GestoreProposta() throws PersistenzaException {


    }

    public PropostaDiScambio trovaPropostaId(long id) throws PropostaNonTrovataException {
        PropostaDiScambioDao propostaDao=FactoryDao.getIstance().ottieniPropostaDiScambioDao();
        return propostaDao.cercaPropostaId(id);




    }

    public GestoreProposta ottieniIstanza(){
        return instance;
    }


}
