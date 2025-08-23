package it.uniroma2.eu.bookcycle.controller.grafica.guicomune;

import it.uniroma2.eu.bookcycle.bean.LoginBean;
import it.uniroma2.eu.bookcycle.controller.applicativo.LoginController;
import it.uniroma2.eu.bookcycle.model.eccezioni.*;
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

    protected void loginCliente(ActionEvent event, TextField usernameLabel, PasswordField passwordField, String path) {
        LoginBean loginBean = new LoginBean();
        loginBean.setUsername(usernameLabel.getText());
        loginBean.setPassword(passwordField.getText());
        LoginController loginController = null;
        try {
            loginController = new LoginController();
        } catch (PersistenzaException _) {
            showAlert("Errore tecnico. Riprovare più tardi");
            return;
        }

        RuoloCliente ruoloCliente;
        try {
            ruoloCliente = loginController.login(loginBean);
        } catch (CredenzialiSbagliateException _) {
            showAlert("Credenziali errate");
            passwordField.clear();
            usernameLabel.clear();
            return;
        } catch (BeanInvalidoException _) {
            showAlert("Inserisci username e password");
            return;
        } catch (ClienteNonTrovatoException _) {
            showAlert("Utente non trovato. Registrati prima di fare il login.");
            return;
        }

        showAlert("Login avvenuto");
        switch (ruoloCliente) {
            case UTENTE -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException _) {
                    showAlert("Errore tecnico: impossibile aprire la schermata utente.");
                }
            }
            case LIBRAIO ->
                    goToLibraio();

            default -> showAlert("Ruolo non riconosciuto.");
        }
    }




protected abstract void goToLibraio();

}



