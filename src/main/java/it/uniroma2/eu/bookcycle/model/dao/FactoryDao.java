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
            try(InputStream input = FactoryDao.class.getClassLoader().getResourceAsStream("proprieta.properties");){
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
//public static FactoryDao getIstance() {
//    if (istance == null) {
//        System.out.println(" Inizio getIstance()");
//
//        try {
//            URL url = FactoryDao.class.getClassLoader()
//                    .getResource("proprieta.properties");
//
//            if (url == null) {
//                System.err.println("File 'proprieta.properties' NON trovato nel classpath.");
//                throw new FileNotFoundException("File non trovato nel classpath.");
//            } else {
//                System.out.println("✅ [DEBUG] File 'proprieta.properties' trovato correttamente.");
//            }
//
//            try (InputStream input = url.openStream()) {
//                Properties properties = new Properties();
//                properties.load(input);
//                System.out.println(" File caricato.");
//
//                String option = properties.getProperty("DAOMODE");
//                System.out.println(" Valore DAOMODE = " + option);
//
//                if (option == null) {
//                    System.err.println(" DAOMODE è null! Verifica che sia presente nel file.");
//                    throw new RuntimeException("DAOMODE non presente nel file .properties");
//                }
//
//                switch (option) {
//                    case "FILE" -> {
//
//                        istance = new FileFactoryDao();
//                    }
//                    case "MEMORY" -> {
//
//                        istance = new MemoryFactoryDao();
//                    }
//                    default -> {
//                        System.err.println("Valore DAOMODE non valido: " + option);
//                        throw new IllegalArgumentException("DAOMODE non valido: " + option);
//                    }
//                }
//            }
//
//        } catch (IOException e) {
//            System.err.println(" Errore nel caricamento del file .properties");
//            e.printStackTrace(); // usiamo direttamente printStackTrace sullo stack primario
//            throw new RuntimeException("Errore nel caricamento delle proprietà", e);
//        }
//
//        System.out.println("Istanza creata con successo.");
//    }
//
//    return istance;
//}


    public abstract AnnuncioDao ottieniAnnuncioDao();
    public abstract LibroScambioDao ottieniLibroScambioDao();
    public abstract LIbroVenNolDao ottieniLibroVeNolDao();
    public abstract ClienteDao ottieniClienteDao();
    public abstract PropostaDiScambioDao ottieniPropostaDiScambioDao();
}

