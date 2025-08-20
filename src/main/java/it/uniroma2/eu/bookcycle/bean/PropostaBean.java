package it.uniroma2.eu.bookcycle.bean;

import it.uniroma2.eu.bookcycle.model.domain.Libro;
import it.uniroma2.eu.bookcycle.model.domain.Utente;

public class PropostaBean extends PropostaParzialeBean{

    private String usernameDestinatario;
    private long idLibroD;

    public boolean completo(){
       return usernameDestinatario != null|| idLibroD != 0;
    }


    public String getDestinatario() {

        return usernameDestinatario;
    }

    public void setDestinatario(String usernameDestinatario) {
        this.usernameDestinatario = usernameDestinatario;
    }



    public long getLibroRichiesto() {
        return idLibroD;
    }

    public void setLibroRichiesto(long idLibroD) {
        this.idLibroD = idLibroD;
    }
}
