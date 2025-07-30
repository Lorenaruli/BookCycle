package it.uniroma2.eu.bookcycle.model.dao.memory;

import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.dao.PropostaDiScambioDao;
import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class PropostaDiScambioDaoMemory implements PropostaDiScambioDao {

        private final List<PropostaDiScambio> proposte = new ArrayList<>();

        @Override
        public void salvaRichiesta(PropostaDiScambio proposta) throws DaoException {
            if (proposta == null) throw new DaoException("Proposta nulla");
            proposte.add(proposta);
        }

        @Override
        public void rimuoviRichiesta(PropostaDiScambio proposta) throws DaoException {
            if (!proposte.remove(proposta)) {
                throw new DaoException("Proposta non trovata");
            }
        }

        @Override
        public List<PropostaDiScambio> getRichiesteRicevute(String usernameDestinatario) throws DaoException {
            return proposte.stream()
                    .filter(p -> p.getDestinatario().getUsername().equalsIgnoreCase(usernameDestinatario))
                    .collect(Collectors.toList());
        }

        @Override
        public List<PropostaDiScambio> getRichiesteInviate(String usernameMittente) throws DaoException {
            return proposte.stream()
                    .filter(p -> p.getMittente().getUsername().equalsIgnoreCase(usernameMittente))
                    .collect(Collectors.toList());
        }

        @Override
        public List<PropostaDiScambio> getTutteRichieste() throws DaoException {
            return new ArrayList<>(proposte);
        }
    }

