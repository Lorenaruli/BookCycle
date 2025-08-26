package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.domain.Utente;
import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.dao.memory.MemoryFactoryDao;
import it.uniroma2.eu.bookcycle.model.domain.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


 class TestLibroDao {
    private ClienteDao clienteDao;

    @BeforeEach
    void setup() throws PersistenzaException {
       clienteDao = FactoryDao.getIstance().ottieniClienteDao();
    }

    @Test
    void testAggiungiLibroAUtente() throws Exception {
       clienteDao.aggiungiUtente("giulia", "pass", "789", "giulia@mail.it");

       Utente giulia = (Utente) clienteDao.ottieniCliente("giulia");
       Libro libro = new Libro("Titolo", "Autore", "Genere", 1L);

       giulia.aggiungiLibro(libro);
       clienteDao.aggiornaCliente(giulia);

       Utente ricaricato = (Utente) clienteDao.ottieniCliente("giulia");
       assertEquals(1, ricaricato.getLibri().size());
       assertEquals("Titolo", ricaricato.getLibri().get(0).getTitolo());
    }
 }


