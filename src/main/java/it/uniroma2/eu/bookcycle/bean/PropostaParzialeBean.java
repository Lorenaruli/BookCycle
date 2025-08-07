package it.uniroma2.eu.bookcycle.bean;

import it.uniroma2.eu.bookcycle.model.domain.Libro;
import it.uniroma2.eu.bookcycle.model.domain.Utente;

public class PropostaParzialeBean {
    private String usernamePropietario;
    private long idLibro;

    public boolean completo(){
        return usernamePropietario != null || idLibro!=0;
    }


    public String getDestinatario() {

        return usernamePropietario;
    }

    public void setDestinatario(String username) {
        this.usernamePropietario = username;
    }



    public long getLibroRichiesto() {
        return idLibro;
    }

    public void setLibroRichiesto(long id) {
        this.idLibro = id;
    }
}

