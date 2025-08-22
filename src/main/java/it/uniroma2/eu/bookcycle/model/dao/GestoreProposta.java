package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PropostaNonTrovataException;
import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;

public class GestoreProposta {
    PropostaDiScambioDao proposta;

    public GestoreProposta() throws PersistenzaException {
        this.proposta=FactoryDao.getIstance().ottieniPropostaDiScambioDao();

    }

    public PropostaDiScambio trovaPropostaId(long id) throws PropostaNonTrovataException {
        return proposta.cercaPropostaId(id);




    }


}
