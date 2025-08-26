package it.uniroma2.eu.bookcycle.model.dao.memory;

import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.dao.AnnuncioDao;
import it.uniroma2.eu.bookcycle.model.domain.Annuncio;
import it.uniroma2.eu.bookcycle.model.domain.TipoAnnuncio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnnuncioDaoMemory implements AnnuncioDao {
    private static AnnuncioDaoMemory instanza;
    boolean idCounterInizializzato = false;

    private List<Annuncio> annunci;

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
    public  void aggiornaIdCounter(){
        if (!idCounterInizializzato) {
            Annuncio.setIdCounter(0);
            idCounterInizializzato = true;
        }
        long max = 0;
        for (Annuncio a : annunci) {
            if (a.getIdAnnuncio() > max) {
                max = a.getIdAnnuncio();
            }
        }
        Annuncio.setIdCounter(max + 1);
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
        return new ArrayList<>(annunci);
    }


    @Override
    public List<Annuncio> cercaPerTitolo(String titolo) {
        if (titolo == null) {
            return Collections.emptyList();
        }
        List<Annuncio> risultati = new ArrayList<>();
        for (Annuncio a : annunci) {
            if (a.getLibro().getTitolo().toLowerCase().contains(titolo.toLowerCase())) {
                risultati.add(a);
            }
        }
        return risultati;
    }

    @Override
    public List<Annuncio> cercaPerAutore(String autore) {
        if (autore == null) {
            return Collections.emptyList();
        }
        List<Annuncio> risultati = new ArrayList<>();
        for (Annuncio a : annunci) {
            if (a.getLibro().getAutore().toLowerCase().contains(autore.toLowerCase())) {
                risultati.add(a);
            }
        }
        return risultati;
    }

    @Override
    public List<Annuncio> cercaPerGenere(String genere) {
        if (genere == null) {
            return Collections.emptyList();
        }
        List<Annuncio> risultati = new ArrayList<>();
        for (Annuncio a : annunci) {
            if (a.getLibro().getGenere().toLowerCase().contains(genere.toLowerCase())) {
                risultati.add(a);
            }
        }
        return risultati;
    }

    @Override
    public List<Annuncio> ottieniAnnunciPerTipo(TipoAnnuncio tipo) {
        if (tipo == null) {
            return Collections.emptyList();
        }
        List<Annuncio> risultati = new ArrayList<>();
        for (Annuncio a : annunci) {
            if (a.getTipo() == tipo) {
                risultati.add(a);
            }
        }
        return risultati;
    }

}
