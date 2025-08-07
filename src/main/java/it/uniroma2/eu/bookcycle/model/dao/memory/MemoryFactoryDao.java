package it.uniroma2.eu.bookcycle.model.dao.memory;

import it.uniroma2.eu.bookcycle.model.dao.*;

public class MemoryFactoryDao extends FactoryDao {
    @Override
    public AnnuncioDao ottieniAnnuncioDao() {

        return AnnuncioDaoMemory.ottieniIstanza();
    }

    @Override
    public LibroScambioDao ottieniLibroScambioDao() {

        return LibroScambioDaoMemory.ottieniIstanza();
    }

    @Override
    public LIbroVenNolDao ottieniLibroVeNolDao() {

        return LibroVenNolDaoMemory.ottieniIstanza();
    }

    @Override
    public ClienteDao ottieniClienteDao() {

        return ClienteDaoMemory.ottieniIstanza();
    }

    @Override
    public PropostaDiScambioDao ottieniPropostaDiScambioDao() {

        return PropostaDiScambioDaoMemory.ottieniIstanza();
    }
}
