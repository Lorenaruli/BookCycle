package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.dao.*;

public class FileFactoryDao extends FactoryDao {
    @Override
    public AnnuncioDao ottieniAnnuncioDao() {
        try {
            return new AnnuncioDaoFile();
        } catch (PersistenzaException e) {
            throw new IllegalStateException("Impossibile creare AnnuncioDaoFile");
        }
    }

    @Override
    public LibroScambioDao ottieniLibroScambioDao() {

        try {
            return new LibroScambioDaoFile();
        } catch (PersistenzaException e) {
            throw new IllegalStateException("Impossibile creare LibroScambioDaoFile");
        }
    }

    @Override
    public LIbroVenNolDao ottieniLibroVeNolDao() {
        try {
            return new LibroVenNolDaoFile();
        } catch (PersistenzaException e) {
            throw new IllegalStateException("Impossibile creare LibroVenditaDaoFile");
        }
    }

    @Override
    public ClienteDao ottieniClienteDao() {

        try {
            return new ClienteDaoFile();
        } catch (PersistenzaException e) {
            throw new IllegalStateException("Impossibile creare AnnuncioDaoFile");
        }
    }

    @Override
    public PropostaDiScambioDao ottieniPropostaDiScambioDao() {
        try {
            return new PropostaDiScambioDaoFile();
        } catch (PersistenzaException e) {
            throw new IllegalStateException("Impossibile creare PropostaDaoFile");
        }
    }
}
