package it.uniroma2.eu.bookcycle.controller.gui2;

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/eu/bookcycle/gui2/LibriScambioView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void libriVendita(ActionEvent event) {
        showAlert("La sezione per i libri da comprare non è disponibile.");

    }

}

