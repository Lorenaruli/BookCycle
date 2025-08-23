package it.uniroma2.eu.bookcycle.controller.grafica.gui2;

import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.SceneManager;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.GraphicController;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.ViewPath;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
        SceneManager.cambiaScena(event, ViewPath.LIBRI_SCAMBIO_VIEW);

    }

    @FXML
    void libriVendita(ActionEvent event) {
        showAlert("La sezione per i libri da comprare non è disponibile.");

    }

}

