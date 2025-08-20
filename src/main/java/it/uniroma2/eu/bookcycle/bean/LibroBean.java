package it.uniroma2.eu.bookcycle.bean;

public class LibroBean {
    String titolo;
    String autore;
    String genere;
    long id;
    String usernameProprietario;

    public LibroBean(String titolo, String autore, String genere, long id, String username) {
        this.titolo = titolo;
        this.autore = autore;
        this.genere = genere;
        this.id = id;
        this.usernameProprietario=username;


    }

    public LibroBean() {
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public long getIdLibro() {
        return id;
    }


    public String getUsernameProprietario() {
        return usernameProprietario;
    }

    public void setUsernameProprietario(String usernameProprietario) {
        this.usernameProprietario = usernameProprietario;
    }


}
