package it.uniroma2.eu.bookcycle.grafica.gui;

import it.uniroma2.eu.bookcycle.bean.LoginBean;
import it.uniroma2.eu.bookcycle.controller.LoginController;
import it.uniroma2.eu.bookcycle.model.domain.RuoloCliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInViewController extends GraphicController {

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
        LoginBean loginBean = new LoginBean();
        loginBean.setUsername(usernameLabel.getText());
        loginBean.setPassword(passwordField.getText());
        //loginBean.setRuolo(libraioCheck.isSelected() ? RuoloCliente.LIBRAIO : RuoloCliente.UTENTE);
        LoginController loginController = new LoginController();
        RuoloCliente ruoloCliente;
        try {
            ruoloCliente = loginController.login(loginBean);
        } catch (RuntimeException e) {
            showAlert("credenziali errate");
            return;
        }
        showAlert("login avvenuto");
        try {
            switch (ruoloCliente) {
                case UTENTE -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/eu/bookcycle/gui/ProfiloView.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    //stage.setTitle("Profilo Utente");
                    stage.show();
                }

                case LIBRAIO -> showAlert("La sezione per i librai non è disponibile.");
                default -> showAlert("Ruolo non riconosciuto.");
            }

        } catch (IOException e) {
            showAlert("Errore nel caricamento della schermata profilo.");
            e.printStackTrace();
        }
    }

    @FXML
    void tornaARegistrazione(ActionEvent event) {
        try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/eu/bookcycle/gui/RegistrazioneView.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        //stage.setTitle("Registrazione");
        stage.show();
    } catch (IOException e) {
        showAlert("Errore nel caricamento della schermata registrazione.");
        e.printStackTrace();
    }


    }

}


