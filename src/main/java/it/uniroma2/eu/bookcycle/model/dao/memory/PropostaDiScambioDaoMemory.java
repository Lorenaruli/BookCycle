package it.uniroma2.eu.bookcycle.model.dao.memory;

import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PropostaNonTrovataException;
import it.uniroma2.eu.bookcycle.model.dao.PropostaDiScambioDao;
import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;

import java.util.ArrayList;
import java.util.List;

public class PropostaDiScambioDaoMemory implements PropostaDiScambioDao {

    private List<PropostaDiScambio> proposteTotali;
    private static PropostaDiScambioDaoMemory instanza;
    boolean idCounterInizializzato = false;

    private PropostaDiScambioDaoMemory() {
        this.proposteTotali = new ArrayList<>();
        aggiornaIdCounter();

    }


    public static PropostaDiScambioDaoMemory ottieniIstanza() {
        if (instanza == null) {
            instanza = new PropostaDiScambioDaoMemory();

        }
        return instanza;
    }

    @Override
    public void aggiungiProposta(PropostaDiScambio proposta) throws OggettoInvalidoException {
        if (proposta == null) {
            throw new OggettoInvalidoException("Proposta nulla");
        }
        for (int i = 0; i < proposteTotali.size(); i++) {
            if (proposteTotali.get(i).getIdProposta() == proposta.getIdProposta()) {
                proposteTotali.set(i, proposta);
                return;
            }
        }
        proposteTotali.add(proposta);
    }

    //l'ho aggiunto per il test1
    public void clear() {
        proposteTotali.clear();
    }


    @Override
    public long aggiornaIdCounter() {
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
        return (max + 1);
    }

    @Override
    public void rimuoviProposta(long idProposta) throws PropostaNonTrovataException {
        PropostaDiScambio daRimuovere = null;

        for (PropostaDiScambio p : proposteTotali) {
            if (p.getIdProposta() == idProposta) {
                daRimuovere = p;
                break;
            }
        }

        if (daRimuovere == null) {
            throw new PropostaNonTrovataException("Proposta non trovata");
        }

        proposteTotali.remove(daRimuovere);
    }


    @Override
    public List<PropostaDiScambio> cercaPropostaLibroOfferto(long idLibro) {
        List<PropostaDiScambio> risultati = new ArrayList<>();
        for (PropostaDiScambio p : proposteTotali) {
            if (p.getLibroOfferto() != null && p.getLibroOfferto().getIdLibro() == idLibro) {
                risultati.add(p);
            }
        }
        return risultati;
    }

    @Override
    public List<PropostaDiScambio> cercaPropostaLibroRichiesto(long idLibro) {
        List<PropostaDiScambio> risultati = new ArrayList<>();
        for (PropostaDiScambio p : proposteTotali) {
            if (p.getLibroRichiesto() != null && p.getLibroRichiesto().getIdLibro() == idLibro) {
                risultati.add(p);
            }
        }
        return risultati;
    }

    @Override
    public PropostaDiScambio cercaPropostaId(long idProposta) throws PropostaNonTrovataException {
        for (PropostaDiScambio p : proposteTotali) {
            if (p.getIdProposta() == idProposta) {
                return p;
            }
        }
        throw new PropostaNonTrovataException("Nessuna proposta trovata con id: " + idProposta);
    }
}


