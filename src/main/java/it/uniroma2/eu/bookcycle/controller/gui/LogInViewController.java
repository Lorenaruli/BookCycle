package it.uniroma2.eu.bookcycle.controller.gui;

import it.uniroma2.eu.bookcycle.controller.SceneManager;
import it.uniroma2.eu.bookcycle.controller.guiComune.LoginClienteGui;
import it.uniroma2.eu.bookcycle.model.Eccezioni.GuiException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


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
        String path = "/it/uniroma2/eu/bookcycle/gui/ProfiloUtenteView.fxml";
        loginCliente(event, usernameLabel, passwordField, path);
    }

    @FXML
    void tornaARegistrazione(ActionEvent event) {
        SceneManager.cambiaScena(event, "/it/uniroma2/eu/bookcycle/gui/RegistrazioneView.fxml");

    }

    @Override
    protected void goToLibraio() {
        try {
            String path = "/it/uniroma2/eu/bookcycle/gui/ProfiloLibraioView.fxml";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            if (loader.getLocation() == null) {
                throw new IllegalStateException("FXML non trovato: " + path);
            }

            Parent root = loader.load();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            showAlert("Errore caricamento ProfiloLibraioView");
        }
    }
}


