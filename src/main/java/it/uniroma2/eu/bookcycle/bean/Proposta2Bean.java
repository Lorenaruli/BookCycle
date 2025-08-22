package it.uniroma2.eu.bookcycle.bean;

public class Proposta2Bean extends PropostaBean {
    private long idProposta;
    private String titoloRichiesto;
   private String titoloOfferto;


    public long getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(long idProposta) {
        this.idProposta = idProposta;
    }


    public String getTitoloRichiesto() {
        return titoloRichiesto;
    }

    public void setTitoloRichiesto(String titoloRichiesto) {
        this.titoloRichiesto = titoloRichiesto;
    }

    public String getTitoloOfferto() {
        return titoloOfferto;
    }

    public void setTitoloOfferto(String titoloOfferto) {
        this.titoloOfferto = titoloOfferto;
    }
}
