package it.uniroma2.eu.bookcycle.bean;

import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.domain.RuoloCliente;

public class RegistrazioneBean {
    private String username;
    private String telefono;
    private String email;
    private String password;
    private RuoloCliente ruolo;


    public boolean completo(){
        return username != null || telefono != null || email != null || password != null || ruolo != null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws OggettoInvalidoException {
        if (username == null || username.isEmpty()){
            throw new OggettoInvalidoException("l' username deve avere almeno un carattere");
        }
        this.username = username;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RuoloCliente getRuolo() {
        return ruolo;
    }

    public void setRuolo(RuoloCliente ruolo) {
        this.ruolo = ruolo;
    }
}
