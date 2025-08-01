package it.uniroma2.eu.bookcycle.model.dao.file;

import it.uniroma2.eu.bookcycle.model.dao.*;

public class FileFactoryDao extends FactoryDao {
    @Override
    public AnnuncioDao ottieniAnnuncioDao() {
        return new AnnuncioDaoFile();
    }

    @Override
    public LibroDao ottieniLibroDao() {
        return new LibroScambioDaoFile();
    }

    @Override
    public ClienteDao ottieniClienteDao() {
        return new ClienteDaoFile();
    }

    @Override
    public PropostaDiScambioDao PropostaDiScambioDao() {
        return new PropostaDiScambioDaoFile();
    }
}
