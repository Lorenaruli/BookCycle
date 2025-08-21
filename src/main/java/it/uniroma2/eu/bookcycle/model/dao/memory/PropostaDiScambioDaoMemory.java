package it.uniroma2.eu.bookcycle.model.dao.memory;

import it.uniroma2.eu.bookcycle.model.Eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PropostaNonTrovataException;
import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.dao.PropostaDiScambioDao;
import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class PropostaDiScambioDaoMemory implements PropostaDiScambioDao {

    private  List<PropostaDiScambio> proposteTotali;
    private static PropostaDiScambioDaoMemory instanza;
    static boolean idCounterInizializzato = false;

    private PropostaDiScambioDaoMemory(){
        this.proposteTotali=new ArrayList<>();
        aggiornaIdCounter();

    }


    public static PropostaDiScambioDaoMemory ottieniIstanza(){
        if (instanza == null){
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
    public List<PropostaDiScambio> getProposteRicevute(String usernameDestinatario) {
        return proposteTotali.stream()
                .filter(p -> p.getDestinatario().getUsername().equalsIgnoreCase(usernameDestinatario))
                .collect(Collectors.toList());
    }

    @Override
    public List<PropostaDiScambio> getProposteInviate(String usernameMittente){
        return proposteTotali.stream()
                .filter(p -> p.getMittente().getUsername().equalsIgnoreCase(usernameMittente))
                .collect(Collectors.toList());
    }

    @Override
    public List<PropostaDiScambio> cercaPropostaLibroOfferto(long idLibro) {
        return proposteTotali.stream()
                .filter(p -> p.getLibroOfferto() != null
                        && p.getLibroOfferto().getIdLibro() == idLibro)
                .toList();

    }



    @Override
    public List<PropostaDiScambio> cercaPropostaLibroRichiesto(long idLibro) {
        return proposteTotali.stream()
                .filter(p -> p.getLibroRichiesto() != null
                        && p.getLibroRichiesto().getIdLibro() == idLibro)
                .toList();

    }

    @Override
    public PropostaDiScambio cercaPropostaId(long idProposta) throws PropostaNonTrovataException {
        return proposteTotali.stream()
                .filter(p -> p.getIdProposta() == idProposta)
                .findFirst()
                .orElseThrow(() ->
                        new PropostaNonTrovataException("Nessuna proposta trovata con id: " + idProposta));

    }
}


