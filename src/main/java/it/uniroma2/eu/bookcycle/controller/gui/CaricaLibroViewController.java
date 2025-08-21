package it.uniroma2.eu.bookcycle.controller.gui;

import it.uniroma2.eu.bookcycle.controller.SceneManager;
import it.uniroma2.eu.bookcycle.controller.guiComune.CaricaLibroGui;
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
            carica(event, titoloField, autoreField, genereField);


        }
        @FXML
        void tornaIndietro(ActionEvent event) {
            SceneManager.cambiaScena(event, "/it/uniroma2/eu/bookcycle/gui/ProfiloUtenteView.fxml");
        }

        @Override
        protected void goToLogin() {
                try {
                        String path = "/it/uniroma2/eu/bookcycle/gui/LoginView.fxml";
                        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
                        Parent root = loader.load();


                        Stage stage = (Stage) tornaIndietroButton.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();

                } catch (IOException e) {
                        showAlert("Errore nel caricamento della schermata di login.");
                }

        }
}
