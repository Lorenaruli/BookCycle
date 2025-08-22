import it.uniroma2.eu.bookcycle.bean.Proposta3Bean;
import it.uniroma2.eu.bookcycle.controller.GestisciPropostaController;
import it.uniroma2.eu.bookcycle.model.Eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.dao.ClienteDao;
import it.uniroma2.eu.bookcycle.model.dao.FactoryDao;
import it.uniroma2.eu.bookcycle.model.dao.PropostaDiScambioDao;
import it.uniroma2.eu.bookcycle.model.dao.memory.MemoryFactoryDao;
import it.uniroma2.eu.bookcycle.model.domain.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGestisciProposta {


    @Test
    public void testGestisciPropostaRifiutataAggiornaStato() throws PersistenzaException, OggettoInvalidoException {
        FactoryDao factory = new MemoryFactoryDao();
        PropostaDiScambioDao propostaDao = FactoryDao.getIstance().ottieniPropostaDiScambioDao();
        ClienteDao clienteDao = FactoryDao.getIstance().ottieniClienteDao();


//

//
////        Utente mittente = (Utente) clienteDao.trovaPerUsername("mario");
////        Utente destinatario = (Utente) clienteDao.trovaPerUsername("luca");
//        Utente mittente=new Utente("diego");
//        Utente destinatario=new Utente("franco");

        clienteDao.aggiungiUtente("diego", "pass", "123", "diego03");
        clienteDao.aggiungiUtente("franco", "pass", "987", "franco03");

        Utente mittente=(Utente)clienteDao.trovaPerUsername("diego");
        Utente destinatario=(Utente)clienteDao.trovaPerUsername("franco");



        PropostaDiScambio proposta = new PropostaDiScambio(mittente, destinatario,
                new Libro("Titolo1", "Autore1", "Genere1", "diego"),
                new Libro("Titolo2", "Autore2", "Genere2","franco"),0, StatoProposta.IN_ATTESA);
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
        propostaDao.rimuoviProposta(0);
        clienteDao.rimuoviCliente(mittente.getUsername());
        clienteDao.rimuoviCliente(mittente.getUsername());
        //assertEquals("Titolo1", aggiornata.getLibroOfferto().getTitolo());
    }
    }
