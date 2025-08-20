package it.uniroma2.eu.bookcycle.controller.gui;

import it.uniroma2.eu.bookcycle.controller.SceneManager;
import it.uniroma2.eu.bookcycle.controller.guiComune.LoginClienteGui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LogInViewController extends LoginClienteGui {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameLabel;

    @FXML
    private Button registratiButton;

    @FXML
    private Button loginButton;

    @FXML
    void login(ActionEvent event) {
        String path= "/it/uniroma2/eu/bookcycle/gui/ProfiloUtenteView.fxml";
        loginCliente(event, usernameLabel, passwordField,path);
    }

    @FXML
    void tornaARegistrazione(ActionEvent event) {
        SceneManager.cambiaScena(event,"/it/uniroma2/eu/bookcycle/gui/RegistrazioneView.fxml");



    }

}


