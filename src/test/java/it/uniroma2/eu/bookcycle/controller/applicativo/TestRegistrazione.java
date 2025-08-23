package it.uniroma2.eu.bookcycle.controller.applicativo;

import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoEsistenteException;
import it.uniroma2.eu.bookcycle.model.dao.memory.ClienteDaoMemory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class TestRegistrazione {

    @Test
    void testRegistrazioneUtente() throws Exception {
        ClienteDaoMemory clienteDao = ClienteDaoMemory.ottieniIstanza();
        clienteDao.aggiungiUtente("silvio", "password", "43574387", "silvio03");

        assertNotNull(clienteDao.ottieniCliente("silvio"));
        assertEquals("silvio", clienteDao.ottieniCliente("silvio").getUsername());

    }
    @Test
    void testRegistrazioneDuplicata() throws Exception {
        ClienteDaoMemory clienteDao = ClienteDaoMemory.ottieniIstanza();
        clienteDao.aggiungiUtente("fra", "password", "4735457", "fra03");

        assertThrows(OggettoEsistenteException.class, () -> {
            clienteDao.aggiungiUtente("fra", "altraPassword", "8859868", "fra04");
        });
        clienteDao.rimuoviCliente("fra");
    }

}