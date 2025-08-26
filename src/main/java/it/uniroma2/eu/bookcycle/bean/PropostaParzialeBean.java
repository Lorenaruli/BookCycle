package it.uniroma2.eu.bookcycle.bean;

public class PropostaParzialeBean {
    private long idLibroM;


     public boolean completo(){
        return  idLibroM !=0;
    }

    public long getLibroOfferto() {
        return idLibroM;
    }

    public void setLibroOfferto(long id) {
        this.idLibroM = id;
    }
}

