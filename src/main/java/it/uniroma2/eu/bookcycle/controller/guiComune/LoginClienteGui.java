package it.uniroma2.eu.bookcycle.controller.guiComune;

import it.uniroma2.eu.bookcycle.bean.LoginBean;
import it.uniroma2.eu.bookcycle.controller.LoginController;
import it.uniroma2.eu.bookcycle.controller.gui.GraphicController;
import it.uniroma2.eu.bookcycle.model.domain.RuoloCliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class LoginClienteGui extends GraphicController {

    protected void loginCliente(ActionEvent event, TextField usernameLabel, PasswordField passwordField, String path){
        LoginBean loginBean = new LoginBean();
        loginBean.setUsername(usernameLabel.getText());
        loginBean.setPassword(passwordField.getText());
        LoginController loginController = new LoginController();
        RuoloCliente ruoloCliente;
        try {
            ruoloCliente = loginController.login(loginBean);
        } catch (RuntimeException e) {
            showAlert("Credenziali errate");
            return;
        }
        showAlert("Login avvenuto");
        try {
            switch (ruoloCliente) {
                case UTENTE -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                }

                case LIBRAIO -> {goToLibraio();}
                default -> showAlert("Ruolo non riconosciuto.");
            }

        } catch (IOException e) {
            showAlert("Errore nel caricamento della schermata profilo.");
            e.printStackTrace();
        }
    }
    protected abstract void goToLibraio();

}


