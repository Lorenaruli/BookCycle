package it.uniroma2.eu.bookcycle.model.dao.memory;

import it.uniroma2.eu.bookcycle.model.Eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.dao.AnnuncioDao;
import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.domain.Annuncio;
import it.uniroma2.eu.bookcycle.model.domain.TipoAnnuncio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AnnuncioDaoMemory implements AnnuncioDao {
    private static AnnuncioDaoMemory instanza;
    static boolean idCounterInizializzato = false;

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
    public void aggiornaIdCounter(){
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
    public List<Annuncio> cercaPerProprietario(String username) {
        return annunci.stream()
                .filter(a -> a.getLibraio().getUsername().equalsIgnoreCase(username))
                .collect(Collectors.toList());
    }

    @Override
    public void rimuoviAnnuncio(long idAnnuncio) throws OggettoInvalidoException {
        boolean removed = annunci.removeIf(a -> a.getIdAnnuncio() == idAnnuncio);
        if (!removed) {
            throw new OggettoInvalidoException("Annuncio non trovato");
        }
    }

    @Override
    public List<Annuncio> ottieniTuttiAnnunci() throws DaoException {
        return new ArrayList<>(annunci);
    }

    @Override
    public List<Annuncio> ottieniAnnunciPerLibraio(String usernameLibraio) {
        if (usernameLibraio == null) {
            return Collections.emptyList();
        }
        return annunci.stream()
                .filter(a -> a.getLibraio().getUsername().equalsIgnoreCase(usernameLibraio))
                .collect(Collectors.toList());
    }

    @Override
    public List<Annuncio> cercaPerTitolo(String titolo)  {
        if (titolo == null) {
            return Collections.emptyList();
        }
        return annunci.stream()
                .filter(a -> a.getLibro().getTitolo().toLowerCase().contains(titolo.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Annuncio> cercaPerAutore(String autore){
        if (autore == null) {
            return Collections.emptyList();
        }
        return annunci.stream()
                .filter(a -> a.getLibro().getAutore().toLowerCase().contains(autore.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Annuncio> cercaPerGenere(String genere) {
        if (genere == null) {
            return Collections.emptyList();
        }
        return annunci.stream()
                .filter(a -> a.getLibro().getGenere().toLowerCase().contains(genere.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Annuncio> ottieniAnnunciPerTipo(TipoAnnuncio tipo)  {
        if (tipo == null) {
            return Collections.emptyList();
        }
        return annunci.stream()
                .filter(a -> a.getTipo() == tipo)
                .collect(Collectors.toList());
    }
}
