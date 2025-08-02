package it.uniroma2.eu.bookcycle.bean;

import it.uniroma2.eu.bookcycle.model.domain.RuoloCliente;

public class LoginBean {
    private String username;
    private String password;

    public LoginBean() {
    }
    public boolean completo(){
        return username != null || password != null;
    }
    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}







