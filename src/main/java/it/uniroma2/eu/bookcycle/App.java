package it.uniroma2.eu.bookcycle;

import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.ViewPath;
import it.uniroma2.eu.bookcycle.model.dao.FactoryDao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App extends Application {

    private static final Logger logger = Logger.getLogger(App.class.getName());

    @Override
    public void start(Stage stage) throws Exception {
        InputStream input = FactoryDao.class.getClassLoader().getResourceAsStream("proprieta.properties");
        logger.log(Level.INFO, "Trovato file: {0}", (input != null));

        // Per la Gui2 Partire con ViewPath.SCHERMATA_ACCESSO_VIEW;
        try {
            String path = ViewPath.REGISTRAZIONE_VIEW;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("bookCycle");
            stage.show();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Errore nel caricamento della view", e);
        }
    }

    }

