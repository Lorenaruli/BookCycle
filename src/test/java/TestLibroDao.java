import it.uniroma2.eu.bookcycle.model.dao.FactoryDao;
import it.uniroma2.eu.bookcycle.model.dao.LibroDao;
import it.uniroma2.eu.bookcycle.model.dao.LibroScambioDao;
import it.uniroma2.eu.bookcycle.model.dao.file.LibroScambioDaoFile;
import it.uniroma2.eu.bookcycle.model.dao.memory.LibroScambioDaoMemory;
import it.uniroma2.eu.bookcycle.model.dao.memory.MemoryFactoryDao;
import it.uniroma2.eu.bookcycle.model.domain.Libro;
import it.uniroma2.eu.bookcycle.model.domain.StatoLibro;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestLibroDao {
    @Test
    public void testSalvataggioLibro() {

        FactoryDao factory = new MemoryFactoryDao();
        LibroScambioDao dao = factory.ottieniLibroScambioDao();
        //LibroDao dao = new LibroScambioDaoMemory();
        Libro libro = new Libro("Titolo", "Autore", "Genere", "Proprietario", 0);

        dao.aggiungiLibro(libro);

        Libro recuperato = dao.cercaPerId(libro.getIdLibro());
        assertNotNull(recuperato);
        assertEquals("Titolo", recuperato.getTitolo());
        assertEquals("Autore", recuperato.getAutore());
        dao.rimuoviLibro(libro.getIdLibro());
    }
    }


