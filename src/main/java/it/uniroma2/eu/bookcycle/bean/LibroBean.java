package it.uniroma2.eu.bookcycle.bean;

public class LibroBean extends CaricaLibroBean {

    long id;
    String usernameProprietario;

    public LibroBean(String titolo, String autore, String genere, long id, String username) {
        super(titolo, autore, genere);
        this.id = id;
        this.usernameProprietario=username;


    }

  public LibroBean() {
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
