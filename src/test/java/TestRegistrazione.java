import it.uniroma2.eu.bookcycle.bean.LoginBean;
import it.uniroma2.eu.bookcycle.controller.LoginController;
import it.uniroma2.eu.bookcycle.model.Eccezioni.OggettoEsistenteException;
import it.uniroma2.eu.bookcycle.model.dao.memory.ClienteDaoMemory;
import it.uniroma2.eu.bookcycle.model.domain.RuoloCliente;
import it.uniroma2.eu.bookcycle.model.domain.Sessione;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestRegistrazione {

    @Test
    void testRegistrazioneUtente() throws Exception {
        ClienteDaoMemory clienteDao = ClienteDaoMemory.ottieniIstanza();
        clienteDao.aggiungiUtente("luca", "password", "43574387", "mario03");

        assertNotNull(clienteDao.ottieniCliente("luca"));
        assertEquals("luca", clienteDao.ottieniCliente("luca").getUsername());

    }
    @Test
    void testRegistrazioneDuplicata() throws Exception {
        ClienteDaoMemory clienteDao = ClienteDaoMemory.ottieniIstanza();
        clienteDao.aggiungiUtente("mario", "password", "4735457", "mario03");

        assertThrows(OggettoEsistenteException.class, () -> {
            clienteDao.aggiungiUtente("mario", "altraPassword", "8859868", "mario04");
        });
    }
}