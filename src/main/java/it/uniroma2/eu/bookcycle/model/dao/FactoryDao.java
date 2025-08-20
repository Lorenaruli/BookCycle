package it.uniroma2.eu.bookcycle.model.dao;

import it.uniroma2.eu.bookcycle.model.dao.file.FileFactoryDao;
import it.uniroma2.eu.bookcycle.model.dao.memory.MemoryFactoryDao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
            } catch (IOException e) {
                System.exit(1);
            }
        }
        return istance;
    }



    public abstract AnnuncioDao ottieniAnnuncioDao();
    public abstract LibroScambioDao ottieniLibroScambioDao();
    public abstract LIbroVenNolDao ottieniLibroVeNolDao();
    public abstract ClienteDao ottieniClienteDao();
    public abstract PropostaDiScambioDao ottieniPropostaDiScambioDao();
}

