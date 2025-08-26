package it.uniroma2.eu.bookcycle.bean;

public class LibroBean extends CaricaLibroBean {

    long id;

    public LibroBean(String titolo, String autore, String genere, long id) {
        super(titolo, autore, genere);
        this.id = id;
    }

  public LibroBean() {
  }



    public long getIdLibro() {
        return id;
    }




}
