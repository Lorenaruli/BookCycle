package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.dao.file.FileFactoryDao;
import it.uniroma2.eu.bookcycle.model.dao.memory.MemoryFactoryDao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class FactoryDao {
    private static final String PROPERTIES_PATH = "proprieta.properties";
    private static FactoryDao istance;

    public static FactoryDao getIstance(){
        if (istance == null) {
            try(InputStream input = FactoryDao.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH);){
                if (input == null) {
                    throw new FileNotFoundException("File 'proprieta.properties' non trovato nel classpath!");
                }

                Properties properties = new Properties();
                properties.load(input);
                String option = properties.getProperty("DAOMODE");
                switch (option) {
                    case "FILE":
                        istance = new FileFactoryDao();
                        break;
                    case "MEMORY":
                        istance = new MemoryFactoryDao();
                        break;
                    default:
                        System.exit(1);
                }
            } catch (IOException _) {
                System.exit(1);
            }
        }
        return istance;
    }



    public abstract AnnuncioDao ottieniAnnuncioDao() throws PersistenzaException;
    public abstract LibroScambioDao ottieniLibroScambioDao() throws PersistenzaException;
    public abstract LIbroVenNolDao ottieniLibroVeNolDao() throws PersistenzaException;
    public abstract ClienteDao ottieniClienteDao() throws PersistenzaException;
    public abstract PropostaDiScambioDao ottieniPropostaDiScambioDao() throws PersistenzaException;
}

