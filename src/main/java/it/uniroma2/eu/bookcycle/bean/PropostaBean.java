package it.uniroma2.eu.bookcycle.bean;

import it.uniroma2.eu.bookcycle.model.domain.Libro;
import it.uniroma2.eu.bookcycle.model.domain.Utente;

public class PropostaBean {
    private String usernameMittente;
    private String usernameDestinatario;
    private long idLibroM;
    private long idLibroD;

    public String getMittente() {
        return usernameMittente;
    }
    public boolean completo(){
        return usernameMittente != null || usernameDestinatario != null|| idLibroM != 0 || idLibroD != 0;
    }

    public void setMittente(String usernameMittente) {
        this.usernameMittente = usernameMittente;
    }

    public String getDestinatario() {

        return usernameDestinatario;
    }

    public void setDestinatario(String usernameDestinatario) {
        this.usernameDestinatario = usernameDestinatario;
    }

    public long getLibroOfferto() {
        return idLibroM;
    }

    public void setLibroOfferto(long  idLibroM) {
        this.idLibroM = idLibroM;
    }

    public long getLibroRichiesto() {
        return idLibroD;
    }

    public void setLibroRichiesto(long idLibroD) {
        this.idLibroD = idLibroD;
    }
}
