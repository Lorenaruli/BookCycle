package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;

public class GestoreProposta {
    PropostaDiScambioDao proposta;

    public GestoreProposta(){
        this.proposta=FactoryDao.getIstance().ottieniPropostaDiScambioDao();

    }

    public PropostaDiScambio trovaPropostaId(long id){
        return proposta.cercaPropostaId(id);




    }


}
