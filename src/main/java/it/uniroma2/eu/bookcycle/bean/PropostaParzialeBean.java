package it.uniroma2.eu.bookcycle.bean;

public class PropostaParzialeBean {
    private String usernameMittente;
    private long idLibroM;

    public boolean completo(){
        return usernameMittente != null || idLibroM !=0;
    }


    public String getMittente() {

        return usernameMittente;
    }

    public void setMittente(String username) {
        this.usernameMittente = username;
    }



    public long getLibroOfferto() {
        return idLibroM;
    }

    public void setLibroOfferto(long id) {
        this.idLibroM = id;
    }
}

