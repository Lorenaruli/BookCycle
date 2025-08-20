package it.uniroma2.eu.bookcycle.controller.gui2;

import it.uniroma2.eu.bookcycle.controller.SceneManager;
import it.uniroma2.eu.bookcycle.controller.gui.GraphicController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class TipoLibroViewController extends GraphicController {

    @FXML
    private Button comprareButton;

    @FXML
    private Button noleggiareButton;

    @FXML
    private Button scambiareButton;

    @FXML
    void libriNoleggio(ActionEvent event) {
        showAlert("La sezione per i libri da noleggiare non è disponibile.");


    }

    @FXML
    void libriScambio(ActionEvent event) {
        SceneManager.cambiaScena(event,"/it/uniroma2/eu/bookcycle/gui2/LibriScambioView.fxml");

    }

    @FXML
    void libriVendita(ActionEvent event) {
        showAlert("La sezione per i libri da comprare non è disponibile.");

    }

}

