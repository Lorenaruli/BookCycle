package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.dao.*;

public class FileFactoryDao extends FactoryDao {
    @Override
    public AnnuncioDao ottieniAnnuncioDao() {

        return new AnnuncioDaoFile();
    }

    @Override
    public LibroScambioDao ottieniLibroScambioDao() {

        return new LibroScambioDaoFile();
    }

    @Override
    public LIbroVenNolDao ottieniLibroVeNolDao() {
        return new LibroVenNolDaoFile();
    }

    @Override
    public ClienteDao ottieniClienteDao() {

        return new ClienteDaoFile();
    }

    @Override
    public PropostaDiScambioDao ottieniPropostaDiScambioDao() {
        return new PropostaDiScambioDaoFile();
    }
}
