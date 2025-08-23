package it.uniroma2.eu.bookcycle.controller.grafica.guiComune;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager extends GraphicController {


    public static void cambiaScena(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlFile));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException ex) {
            showAlert("Errore caricamento Schermata");
        }
    }
}