package it.uniroma2.eu.bookcycle.controller.grafica.gui2;

import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.SceneManager;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.LogoutGui;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.ViewPath;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Profilo2ViewController extends LogoutGui {

    @FXML
    private Button cercaLibriButton;

    @FXML
    private Button gestisciProposteButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button vediLibriMieiButton;

    @FXML
    void Logout(ActionEvent event) {

        String path= ViewPath.SCHERMATA_ACCESSO_VIEW;
        try {
            logoutCliente(event,path);
        } catch (PersistenzaException e) {
            showAlert("Errore tecnico. Riprovare più tardi.");
        }


    }


    @FXML
    void cercaLibri(ActionEvent event) {
        SceneManager.cambiaScena(event,ViewPath.TIPO_LIBRO_VIEW);

    }

    @FXML
    void gestisciProposte(ActionEvent event) {
        SceneManager.cambiaScena(event,"/it/uniroma2/eu/bookcycle/gui2/ProposteView.fxml");

        }



    @FXML
    void vediLibriMiei(ActionEvent event) {
        SceneManager.cambiaScena(event,ViewPath.LIBRI_MIEI2_VIEW);


    }

}

