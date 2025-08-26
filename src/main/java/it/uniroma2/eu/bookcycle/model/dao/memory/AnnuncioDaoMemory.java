package it.uniroma2.eu.bookcycle.model.dao.memory;

import it.uniroma2.eu.bookcycle.model.AnnuncioDaoComune;
import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.dao.AnnuncioDao;
import it.uniroma2.eu.bookcycle.model.domain.Annuncio;
import it.uniroma2.eu.bookcycle.model.domain.TipoAnnuncio;

import java.util.ArrayList;

import java.util.List;

public class AnnuncioDaoMemory implements AnnuncioDao {
    private static AnnuncioDaoMemory instanza;
    boolean idCounterInizializzato = false;

    private List<Annuncio> annunci;
    private AnnuncioDaoComune helper;

    private AnnuncioDaoMemory() {

        this.annunci = new ArrayList<>();
        aggiornaIdCounter();
    }

    public static AnnuncioDaoMemory ottieniIstanza(){
        if (instanza == null){
            instanza = new AnnuncioDaoMemory();

        }
        return instanza;
    }


    @Override
    public void salvaAnnuncio(Annuncio annuncio) throws OggettoInvalidoException {
        if (annuncio == null) {
            throw new OggettoInvalidoException("Annuncio nullo");
        }
        annunci.add(annuncio);
    }


    @Override
    public  void aggiornaIdCounter() {
        if (!idCounterInizializzato) {
            Annuncio.setIdCounter(0);
            idCounterInizializzato = true;
        }
        helper.aggiornaIdCounter();
    }



    @Override
    public void rimuoviAnnuncio(long idAnnuncio) throws OggettoInvalidoException {
        boolean removed = annunci.removeIf(a -> a.getIdAnnuncio() == idAnnuncio);
        if (!removed) {
            throw new OggettoInvalidoException("Annuncio non trovato");
        }
    }

    @Override
    public List<Annuncio> ottieniTuttiAnnunci() {
        return helper.ottieniTuttiAnnunci();
    }


    @Override
    public List<Annuncio> cercaPerTitolo(String titolo) {
     return helper.cercaPerTitolo(titolo);
    }

    @Override
    public List<Annuncio> cercaPerAutore(String autore) {
        return helper.cercaPerGenere(autore);
    }

    @Override
    public List<Annuncio> cercaPerGenere(String genere) {
       return helper.cercaPerGenere(genere);
    }

    @Override
    public List<Annuncio> ottieniAnnunciPerTipo(TipoAnnuncio tipo) {
       return helper.ottieniAnnunciPerTipo(tipo);
    }

}
