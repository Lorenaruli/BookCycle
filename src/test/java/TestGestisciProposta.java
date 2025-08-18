import it.uniroma2.eu.bookcycle.bean.Proposta3Bean;
import it.uniroma2.eu.bookcycle.controller.GestisciPropostaController;
import it.uniroma2.eu.bookcycle.model.dao.FactoryDao;
import it.uniroma2.eu.bookcycle.model.dao.PropostaDiScambioDao;
import it.uniroma2.eu.bookcycle.model.domain.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGestisciProposta {
    @Test
    public void testGestisciPropostaRifiutataAggiornaStato() {
        PropostaDiScambioDao propostaDao = FactoryDao.getIstance().ottieniPropostaDiScambioDao();

        Utente mittente = new Utente("mario");
        Utente destinatario = new Utente("luca");


        PropostaDiScambio proposta = new PropostaDiScambio(mittente, destinatario,
                new Libro("Titolo1", "Autore1", "Genere1", "mario"),
                new Libro("Titolo2", "Autore2", "Genere2","luca"),0, StatoProposta.IN_ATTESA);
        propostaDao.aggiungiRichiesta(proposta);
        GestisciPropostaController controller =
                new GestisciPropostaController();


        Proposta3Bean bean = new Proposta3Bean();
        bean.setIdProposta(0);
        bean.setStato(StatoProposta.RIFIUTATA);
        controller.gestisci(bean);
        PropostaDiScambio aggiornata = propostaDao.cercaPropostaId(0);
        assertEquals(StatoProposta.RIFIUTATA, aggiornata.getStato());
    }
    }
