package it.uniroma2.eu.bookcycle.model.dao.memory;

import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.dao.PropostaDiScambioDao;
import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class PropostaDiScambioDaoMemory implements PropostaDiScambioDao {

    private  List<PropostaDiScambio> proposteTotali = new ArrayList<>();
    private static PropostaDiScambioDaoMemory instanza;


    public static PropostaDiScambioDaoMemory ottieniIstanza(){
        if (instanza == null){
            instanza = new PropostaDiScambioDaoMemory();

        }
        return instanza;
    }

    @Override
    public void salvaRichiesta(PropostaDiScambio proposta) throws DaoException {
        if (proposta == null) throw new DaoException("Proposta nulla");
        proposteTotali.add(proposta);
    }
    @Override
    public void aggiornaIdCounterDaFile(){
        PropostaDiScambio.setIdCounter(0);;
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

