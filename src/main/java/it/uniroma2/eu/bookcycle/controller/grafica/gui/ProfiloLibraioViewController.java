package it.uniroma2.eu.bookcycle.controller.grafica.gui;


import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.SceneManager;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.LogoutGui;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.ViewPath;
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
        SceneManager.cambiaScena(event, ViewPath.CARICA_ANNUNCIO_VIEW);

    }

    @FXML
    void logout(ActionEvent event) {
        String path=ViewPath.REGISTRAZIONE_VIEW;
        try {
            logoutCliente(event, path);
        } catch (PersistenzaException e) {
            showAlert(("Errore tecnico. Riprovare più tardi."));
        }
    }



}

