package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.Eccezioni.LibroNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.domain.Libro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GestoreLibroScambio {
        private LibroScambioDao libroDao;

        public GestoreLibroScambio() throws PersistenzaException {
            this.libroDao = FactoryDao.getIstance().ottieniLibroScambioDao();
        }

        public Libro restituisciLibro(long id) throws LibroNonTrovatoException {
            return libroDao.cercaPerId(id);
        }

    public List<Libro> restituisciSimili(String usernameCliente, long idRiferimento) throws PersistenzaException {
        List<Libro> tutti = libroDao.cercaPerProprietario(usernameCliente);


        Libro riferimento = tutti.stream()
                .filter(l -> l.getIdLibro() == idRiferimento)
                .findFirst()
                .orElse(null);

        if (riferimento == null) {

            return Collections.emptyList();
        }

        List<Libro> simili = new ArrayList<>();

        simili.addAll(
                tutti.stream()
                        .filter(l -> l.getIdLibro() != riferimento.getIdLibro())
                        .filter(l -> l.getTitolo().equalsIgnoreCase(riferimento.getTitolo()))
                        .toList()
        );

        simili.addAll(
                tutti.stream()
                        .filter(l -> l.getIdLibro() != riferimento.getIdLibro())
                        .filter(l -> !l.getTitolo().equalsIgnoreCase(riferimento.getTitolo()))
                        .filter(l -> l.getAutore().equalsIgnoreCase(riferimento.getAutore()))
                        .toList()
        );

        return simili;
    }
    }


