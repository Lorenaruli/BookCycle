package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.domain.Utente;
import it.uniroma2.eu.bookcycle.model.domain.Libro;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestGestioneUtenteInMemoria {
   @Test
   void testGestioneUtente() {
      Utente utente = new Utente("mario");

      Libro libro1 = new Libro("Il nome della rosa", "Eco", "Romanzo", 1L);
      Libro libro2 = new Libro("1984", "Orwell", "Distopia", 2L);
      Libro libro3 = new Libro("Il piccolo principe", "Saint Exupery", "Fiaba", 3L);


      utente.aggiungiLibro(libro1);
      utente.aggiungiLibro(libro2);
      utente.aggiungiLibro(libro3);

      assertEquals(3, utente.getLibri().size(), "L'utente dovrebbe avere 3 libri");

      assertEquals("Il nome della rosa", utente.getLibri().get(0).getTitolo());
      assertEquals("1984", utente.getLibri().get(1).getTitolo());
      assertEquals("Il piccolo principe", utente.getLibri().get(2).getTitolo());


   }
}
