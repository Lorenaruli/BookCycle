package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.domain.Cliente;
import it.uniroma2.eu.bookcycle.model.domain.Utente;
import it.uniroma2.eu.bookcycle.model.eccezioni.ClienteNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.LibroNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.domain.Libro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GestoreLibroScambio {

    private static final GestoreLibroScambio instance = new GestoreLibroScambio();

        private GestoreLibroScambio() throws PersistenzaException {

        }

    public static GestoreLibroScambio getInstance() {
        return instance;
    }

        public Libro restituisciLibro(long id) throws LibroNonTrovatoException {
            LibroDao libroDao=FactoryDao.getIstance().ottieniLibroScambioDao();
            return libroDao.cercaPerId(id);
        }

    public List<Libro>cercaPerProprietario(String username) {
        try {
            ClienteDao clienteDao=FactoryDao.getIstance().ottieniClienteDao();
            Cliente cliente = clienteDao.ottieniCliente(username);
            if (cliente instanceof Utente utente) {
                return utente.getLibri();
            }
        } catch (ClienteNonTrovatoException _) {
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }

    public List<Libro> restituisciSimili(String usernameCliente, long idRiferimento) {
        List<Libro> tutti = cercaPerProprietario(usernameCliente);

        Libro riferimento = null;
        for (Libro l : tutti) {
            if (l.getIdLibro() == idRiferimento) {
                riferimento = l;
                break;
            }
        }

        if (riferimento == null) {
            return Collections.emptyList();
        }

        List<Libro> simili = new ArrayList<>();

        for (Libro l : tutti) {
            if (l.getIdLibro() != riferimento.getIdLibro()
                    && l.getTitolo().equalsIgnoreCase(riferimento.getTitolo())) {
                simili.add(l);
            }
        }

        for (Libro l : tutti) {
            if (l.getIdLibro() != riferimento.getIdLibro()
                    && !l.getTitolo().equalsIgnoreCase(riferimento.getTitolo())
                    && l.getAutore().equalsIgnoreCase(riferimento.getAutore())) {
                simili.add(l);
            }
        }

        return simili;
    }

}


