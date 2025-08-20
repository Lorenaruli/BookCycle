package it.uniroma2.eu.bookcycle.controller.gui;

import it.uniroma2.eu.bookcycle.controller.SceneManager;
import it.uniroma2.eu.bookcycle.controller.guiComune.CaricaLibroGui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
            SceneManager.cambiaScena(event,"/it/uniroma2/eu/bookcycle/gui/ProfiloView.fxml");
        }

    }
