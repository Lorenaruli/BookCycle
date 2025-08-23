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
    public void testPropostaAggiornaStato() throws PersistenzaException, OggettoInvalidoException {
        FactoryDao factory = new MemoryFactoryDao();
        PropostaDiScambioDao propostaDao = factory.ottieniPropostaDiScambioDao();
        ClienteDao clienteDao = factory.ottieniClienteDao();



        clienteDao.aggiungiUtente("diego", "pass", "123", "diego03");
        clienteDao.aggiungiUtente("franco", "pass", "987", "franco03");

        Utente mittente=(Utente)clienteDao.trovaPerUsername("diego");
        Utente destinatario=(Utente)clienteDao.trovaPerUsername("franco");



        PropostaDiScambio proposta = new PropostaDiScambio(mittente, destinatario,
                new Libro("Titolo1", "Autore1", "Genere1", "diego"),
                new Libro("Titolo2", "Autore2", "Genere2","franco"),0, StatoProposta.RIFIUTATA);



        propostaDao.aggiungiProposta(proposta);
        PropostaDiScambio propostaAggiornata=propostaDao.cercaPropostaId(0);

        assertEquals(StatoProposta.RIFIUTATA, propostaAggiornata.getStato());
        propostaDao.rimuoviProposta(0);
        clienteDao.rimuoviCliente(mittente.getUsername());
        clienteDao.rimuoviCliente(destinatario.getUsername());

    }
    }
