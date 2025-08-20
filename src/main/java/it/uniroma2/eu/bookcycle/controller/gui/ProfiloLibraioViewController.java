package it.uniroma2.eu.bookcycle.controller.gui;


import it.uniroma2.eu.bookcycle.controller.SceneManager;
import it.uniroma2.eu.bookcycle.controller.guiComune.LogoutGui;
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
        logoutCliente(event, path);
    }

    @FXML
    void vediAnnunci(ActionEvent event) {

    }

}

