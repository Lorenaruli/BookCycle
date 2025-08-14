package it.uniroma2.eu.bookcycle.bean;

public class ContattiBean {
    String username;
    String telefono;
    String email;

    public ContattiBean(){

    }

    public ContattiBean(String username, String telefono, String email) {
        this.username = username;
        this.telefono = telefono;
        this.email = email;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
