package it.uniroma2.eu.bookcycle.controller.gui2;

import it.uniroma2.eu.bookcycle.controller.LoginController;
import it.uniroma2.eu.bookcycle.controller.SceneManager;
import it.uniroma2.eu.bookcycle.controller.guiComune.LogoutGui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

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

        String path="/it/uniroma2/eu/bookcycle/gui2/SchermataAccessoView.fxml";
        logoutCliente(event,path);


        }


    @FXML
    void cercaLibri(ActionEvent event) {
        SceneManager.cambiaScena(event,"/it/uniroma2/eu/bookcycle/gui2/TipoLibroView.fxml");

    }

    @FXML
    void gestisciProposte(ActionEvent event) {
        SceneManager.cambiaScena(event,"/it/uniroma2/eu/bookcycle/gui2/ProposteView.fxml");

        }



    @FXML
    void vediLibriMiei(ActionEvent event) {
        SceneManager.cambiaScena(event,"/it/uniroma2/eu/bookcycle/gui2/LibriMiei2View.fxml");


    }

}

