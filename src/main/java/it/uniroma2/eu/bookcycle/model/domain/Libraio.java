package it.uniroma2.eu.bookcycle.model.domain;

import java.util.ArrayList;
import java.util.List;
public class Libraio  extends Cliente {
    private List<Annuncio> annunci;

    // Costruttore


    public Libraio(String username) {
        super(username);
        this.annunci=new ArrayList<>();
    }


    public Libraio(String username, List<Annuncio> annunci) {
        super(username); // chiama il costruttore della superclasse
        this.annunci = annunci;}

    public void aggiungiLibro(Libro libro, Annuncio annuncio)
    { //rispetto a utente deve anche aggiungere o togliere l'annuncio
        libri.add(libro);
        annunci.add(annuncio);
    }
    public void eliminaLibro(Libro libro,Annuncio annuncio){
        libri.remove(libro);
        annunci.remove(annuncio);
    }
}


