package it.uniroma2.eu.bookcycle.model.dao.memory;

import it.uniroma2.eu.bookcycle.model.dao.*;

public class MemoryFactoryDao extends FactoryDao {
    @Override
    public AnnuncioDao ottieniAnnuncioDao() {
        return AnnuncioDaoMemory.ottieniIstanza();
    }

    @Override
    public LibroDao ottieniLibroDao() {
        return null;
    }

    @Override
    public ClienteDao ottieniClienteDao() {
        return null;
    }

    @Override
    public PropostaDiScambioDao PropostaDiScambioDao() {
        return null;
    }
}
