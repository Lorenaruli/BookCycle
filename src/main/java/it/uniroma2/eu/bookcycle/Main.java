package it.uniroma2.eu.bookcycle;

import it.uniroma2.eu.bookcycle.model.dao.FactoryDao;

import java.io.InputStream;

import static javafx.application.Application.launch;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public void main(){
            InputStream input = FactoryDao.class.getClassLoader().getResourceAsStream("proprieta.properties");
            logger.info("Trovato file: " + (input != null));

            launch(App.class);
        }
    }






