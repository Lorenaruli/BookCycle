package it.uniroma2.eu.bookcycle.bean;

public class Proposta2Bean {
    private long idProposta;
    private long idLibroR;
    private long idLibroO;
    private String usernameM;
    private String usernameD;
    private String titoloRichiesto;
    private String titoloOfferto;

    public boolean completo(){
        return idProposta != 0 || idLibroO != 0 || idLibroR!= 0 || usernameM!=null || usernameD!=null;
    }

    public long getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(long idProposta) {
        this.idProposta = idProposta;
    }

    public long getIdLibroR() {
        return idLibroR;
    }

    public void setIdLibroR(long idLibroR) {
        this.idLibroR = idLibroR;
    }

    public long getIdLibroO() {
        return idLibroO;
    }

    public void setIdLibroO(long idLibroO) {
        this.idLibroO = idLibroO;
    }

    public String getUsernameM() {
        return usernameM;
    }

    public void setUsernameM(String usernameM) {
        this.usernameM = usernameM;
    }

    public String getUsernameD() {
        return usernameD;
    }

    public void setUsernameD(String usernameD) {
        this.usernameD = usernameD;
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
