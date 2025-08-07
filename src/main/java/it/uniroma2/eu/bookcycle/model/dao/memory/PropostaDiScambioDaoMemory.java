package it.uniroma2.eu.bookcycle.model.dao.memory;

import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.dao.PropostaDiScambioDao;
import it.uniroma2.eu.bookcycle.model.dao.file.PropostaDiScambioDaoFile;
import it.uniroma2.eu.bookcycle.model.domain.Annuncio;
import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class PropostaDiScambioDaoMemory implements PropostaDiScambioDao {

    private  List<PropostaDiScambio> proposteTotali;
    private static PropostaDiScambioDaoMemory instanza;
    private static boolean idCounterInizializzato = false;

    private PropostaDiScambioDaoMemory(){
        aggiornaIdCounter();
        this.proposteTotali=new ArrayList<>();
    }


    public static PropostaDiScambioDaoMemory ottieniIstanza(){
        if (instanza == null){
            instanza = new PropostaDiScambioDaoMemory();

        }
        return instanza;
    }

    @Override
    public void aggiungiRichiesta(PropostaDiScambio proposta) throws DaoException {
        if (proposta == null) throw new DaoException("Proposta nulla");
        proposteTotali.add(proposta);
    }
    @Override
    public long aggiornaIdCounter(){
        if (!idCounterInizializzato) {
            PropostaDiScambio.setIdCounter(0);
            idCounterInizializzato = true;
        }
        long max = 0;
        for (PropostaDiScambio a : proposteTotali) {
            if (a.getIdProposta() > max) {
                max = a.getIdProposta();
            }
        }
        return (max+1);
    }

    @Override
    public void rimuoviRichiesta(long idProposta) throws DaoException {
        PropostaDiScambio daRimuovere = null;

        for (PropostaDiScambio p : proposteTotali) {
            if (p.getIdProposta() == idProposta) {
                daRimuovere = p;
                break;
            }
        }

        if (daRimuovere == null) {
            throw new DaoException("Proposta non trovata");
        }

        proposteTotali.remove(daRimuovere);
    }

    @Override
    public List<PropostaDiScambio> getProposteRicevute(String usernameDestinatario) throws DaoException {
        return proposteTotali.stream()
                .filter(p -> p.getDestinatario().getUsername().equalsIgnoreCase(usernameDestinatario))
                .collect(Collectors.toList());
    }

    @Override
    public List<PropostaDiScambio> getProposteInviate(String usernameMittente) throws DaoException {
        return proposteTotali.stream()
                .filter(p -> p.getMittente().getUsername().equalsIgnoreCase(usernameMittente))
                .collect(Collectors.toList());
    }



}

