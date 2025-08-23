package it.uniroma2.eu.bookcycle.model.domain;

import java.util.ArrayList;
import java.util.List;
public class Libraio  extends Cliente {
    private List<Annuncio> annunci;



    public Libraio(String username) {
        super(username);
        this.annunci=new ArrayList<>();
    }


    public Libraio(String username, List<Annuncio> annunci) {
        super(username);
        this.annunci = annunci;
    }

    public void aggiungiAnnuncio(Annuncio annuncio)
    {
        annunci.add(annuncio);
    }
    public void eliminaAnnuncio(long idAnnuncio){
        annunci.removeIf(annuncio->annuncio.getIdAnnuncio()==idAnnuncio);
    }
}


