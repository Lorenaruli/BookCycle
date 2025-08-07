package it.uniroma2.eu.bookcycle.model.dao.memory;

import it.uniroma2.eu.bookcycle.model.dao.AnnuncioDao;
import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.domain.Annuncio;
import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;
import it.uniroma2.eu.bookcycle.model.domain.TipoAnnuncio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AnnuncioDaoMemory implements AnnuncioDao {
    private static AnnuncioDaoMemory instanza;
    private static boolean idCounterInizializzato = false;

    private List<Annuncio> annunci;

    private AnnuncioDaoMemory() {
        aggiornaIdCounter();

        this.annunci = new ArrayList<>();
    }

    public static AnnuncioDaoMemory ottieniIstanza(){
        if (instanza == null){
            instanza = new AnnuncioDaoMemory();

        }
        return instanza;
    }


    @Override
    public void salvaAnnuncio(Annuncio annuncio) throws DaoException {
        if (annuncio == null) {
            throw new DaoException("Annuncio nullo");
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
    public void rimuoviAnnuncio(long idAnnuncio) throws DaoException {
        boolean removed = annunci.removeIf(a -> a.getIdAnnuncio() == idAnnuncio);
        if (!removed) {
            throw new DaoException("Annuncio non trovato");
        }
    }

    @Override
    public List<Annuncio> ottieniTuttiAnnunci() throws DaoException {
        return new ArrayList<>(annunci);
    }

    @Override
    public List<Annuncio> ottieniAnnunciPerLibraio(String usernameLibraio) throws DaoException {
        if (usernameLibraio == null) {
            throw new DaoException("Username nullo");
        }
        return annunci.stream()
                .filter(a -> a.getLibraio().getUsername().equalsIgnoreCase(usernameLibraio))
                .collect(Collectors.toList());
    }

    @Override
    public List<Annuncio> cercaPerTitolo(String titolo) throws DaoException {
        if (titolo == null) {
            throw new DaoException("Titolo nullo");
        }
        return annunci.stream()
                .filter(a -> a.getLibro().getTitolo().toLowerCase().contains(titolo.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Annuncio> cercaPerAutore(String autore) throws DaoException {
        if (autore == null) {
            throw new DaoException("Autore nullo");
        }
        return annunci.stream()
                .filter(a -> a.getLibro().getAutore().toLowerCase().contains(autore.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Annuncio> cercaPerGenere(String genere) throws DaoException {
        if (genere == null) {
            throw new DaoException("Genere nullo");
        }
        return annunci.stream()
                .filter(a -> a.getLibro().getGenere().toLowerCase().contains(genere.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Annuncio> ottieniAnnunciPerTipo(TipoAnnuncio tipo) throws DaoException {
        if (tipo == null) {
            throw new DaoException("Tipo nullo");
        }
        return annunci.stream()
                .filter(a -> a.getTipo() == tipo)
                .collect(Collectors.toList());
    }
}
