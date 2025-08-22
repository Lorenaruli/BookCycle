package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.dao.*;

public class FileFactoryDao extends FactoryDao {
    @Override
    public AnnuncioDao ottieniAnnuncioDao() throws PersistenzaException{

        return new AnnuncioDaoFile();

    }

    @Override
    public LibroScambioDao ottieniLibroScambioDao() throws PersistenzaException {

            return new LibroScambioDaoFile();

    }

    @Override
    public LIbroVenNolDao ottieniLibroVeNolDao() throws PersistenzaException {
        return new LibroVenNolDaoFile();
    }

    @Override
    public ClienteDao ottieniClienteDao() throws PersistenzaException {

       return new ClienteDaoFile();
    }

    @Override
    public PropostaDiScambioDao ottieniPropostaDiScambioDao() throws PersistenzaException {
       return new PropostaDiScambioDaoFile();
    }
}
