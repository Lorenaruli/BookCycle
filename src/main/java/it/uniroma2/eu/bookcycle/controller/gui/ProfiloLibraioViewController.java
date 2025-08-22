package it.uniroma2.eu.bookcycle.controller.gui;


import it.uniroma2.eu.bookcycle.controller.SceneManager;
import it.uniroma2.eu.bookcycle.controller.guiComune.LogoutGui;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ProfiloLibraioViewController extends LogoutGui {

    @FXML
    private Button caricaButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button vediButton;

    @FXML
    void caricaAnnuncio(ActionEvent event) {
        SceneManager.cambiaScena(event,"/it/uniroma2/eu/bookcycle/gui/CaricaAnnunciView.fxml");

    }

    @FXML
    void logout(ActionEvent event) {
        String path="/it/uniroma2/eu/bookcycle/gui/RegistrazioneView.fxml";
        try {
            logoutCliente(event, path);
        } catch (PersistenzaException e) {
            showAlert(("Errore tecnico. Riprovare più tardi."));
        }
    }

    @FXML
    void vediAnnunci(ActionEvent event) {

    }

}

