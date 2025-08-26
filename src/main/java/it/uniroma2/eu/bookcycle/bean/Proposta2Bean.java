package it.uniroma2.eu.bookcycle.bean;

import it.uniroma2.eu.bookcycle.model.domain.StatoProposta;

public class Proposta2Bean extends PropostaBean {
    private long idProposta;
    private StatoProposta stato;
   private String titoloRichiesto;



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

        public String getTitoloRichiesto() {
        return titoloRichiesto;
    }

    public void setTitoloRichiesto(String titoloRichiesto) {
        this.titoloRichiesto = titoloRichiesto;
    }

}
