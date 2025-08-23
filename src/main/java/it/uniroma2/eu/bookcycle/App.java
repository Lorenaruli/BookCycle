package it.uniroma2.eu.bookcycle;

import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.ViewPath;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {

            String path = ViewPath.REGISTRAZIONE_VIEW;  //ViewPath.SCHERMATA_ACCESSO_VIEW;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("bookCycle");
            stage.show();
        }

    }

