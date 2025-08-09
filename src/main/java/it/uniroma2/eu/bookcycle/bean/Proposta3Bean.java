package it.uniroma2.eu.bookcycle.bean;

import it.uniroma2.eu.bookcycle.model.domain.StatoProposta;

public class Proposta3Bean {
    private long idProposta ;
    private StatoProposta stato;

    public boolean completo(){
        return idProposta!=0 || stato!=null;
    }

    public long getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(long idProposta) {
        this.idProposta = idProposta;
    }

    public StatoProposta getStato() {
        return stato;
    }

    public void setStato(StatoProposta stato) {
        this.stato = stato;
    }
}
