package it.uniroma2.eu.bookcycle.controller.grafica.gui;

import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.SceneManager;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.CaricaLibroGui;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.ViewPath;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CaricaLibroViewController extends CaricaLibroGui {


        @FXML
        private TextField autoreField;

        @FXML
        private Button caricaButton;

        @FXML
        private TextField genereField;

        @FXML
        private TextField titoloField;

        @FXML
        private Button tornaIndietroButton;

        @FXML
        void caricaLibro(ActionEvent event) {
            carica( titoloField, autoreField, genereField);


        }
        @FXML
        void tornaIndietro(ActionEvent event) {
            SceneManager.cambiaScena(event, ViewPath.PROFILO_UTENTE_VIEW);
        }

        @Override
        protected void goToLogin() {
                try {
                        String path = ViewPath.LOGIN_VIEW;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
                        Parent root = loader.load();


                        Stage stage = (Stage) tornaIndietroButton.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();

                } catch (IOException _) {
                        showAlert("Errore nel caricamento della schermata di login.");
                }

        }
}
