import it.uniroma2.eu.bookcycle.bean.Proposta3Bean;
import it.uniroma2.eu.bookcycle.controller.GestisciPropostaController;
import it.uniroma2.eu.bookcycle.model.Eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.dao.FactoryDao;
import it.uniroma2.eu.bookcycle.model.dao.PropostaDiScambioDao;
import it.uniroma2.eu.bookcycle.model.domain.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGestisciProposta {
    @Test
    public void testGestisciPropostaRifiutataAggiornaStato() throws PersistenzaException, OggettoInvalidoException {
        PropostaDiScambioDao propostaDao = FactoryDao.getIstance().ottieniPropostaDiScambioDao();

        Utente mittente = new Utente("mario");
        Utente destinatario = new Utente("luca");


        PropostaDiScambio proposta = new PropostaDiScambio(mittente, destinatario,
                new Libro("Titolo1", "Autore1", "Genere1", "mario"),
                new Libro("Titolo2", "Autore2", "Genere2","luca"),0, StatoProposta.IN_ATTESA);
        propostaDao.aggiungiProposta(proposta);
        GestisciPropostaController controller =
                new GestisciPropostaController();


        Proposta3Bean bean = new Proposta3Bean();
        bean.setIdProposta(0);
        bean.setStato(StatoProposta.RIFIUTATA);
        controller.gestisci(bean);
        PropostaDiScambio aggiornata = propostaDao.cercaPropostaId(bean.getIdProposta());
        //assertEquals("luca", proposta.getDestinatario().getUsername());
        assertEquals(bean.getStato(), aggiornata.getStato());
        //assertEquals("Titolo1", aggiornata.getLibroOfferto().getTitolo());
    }
    }
