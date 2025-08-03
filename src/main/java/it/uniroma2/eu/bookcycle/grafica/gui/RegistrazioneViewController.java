package it.uniroma2.eu.bookcycle.grafica.gui;

import it.uniroma2.eu.bookcycle.bean.LoginBean;
import it.uniroma2.eu.bookcycle.bean.RegistrazioneBean;
import it.uniroma2.eu.bookcycle.controller.LoginController;
import it.uniroma2.eu.bookcycle.controller.RegistrazioneController;
import it.uniroma2.eu.bookcycle.model.domain.Cliente;
import it.uniroma2.eu.bookcycle.model.domain.RuoloCliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrazioneViewController extends GraphicController {

    @FXML
    private TextField emailLabel;

    @FXML
    private CheckBox libraioCheck;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField telephoneLabel;

    @FXML
    private TextField usernameLabel;
    @FXML
    private Button PrecedentRegistratoButton;

    @FXML
    private Button RegistratiButton;

    @FXML
    void registra(ActionEvent event) {
        RegistrazioneBean registrazioneBean = new RegistrazioneBean();
        registrazioneBean.setUsername(usernameLabel.getText());
        registrazioneBean.setPassword(passwordField.getText());
        registrazioneBean.setEmail(emailLabel.getText());
        registrazioneBean.setTelefono(telephoneLabel.getText());
        registrazioneBean.setRuolo(libraioCheck.isSelected() ? RuoloCliente.LIBRAIO : RuoloCliente.UTENTE);
        RegistrazioneController registrazioneController = new RegistrazioneController();
        Cliente cliente;
        try {
            cliente = registrazioneController.registra(registrazioneBean);
        } catch (RuntimeException e) {
            showAlert("username gia scelto");
            return;
        }
        showAlert("login avvenuto");
//        switch (ruoloCliente){
//            case LIBRAIO -> //apri menu libraio
//            case UTENTE -> //apri menu utente
//            default -> // errore
//        }


    }

    @FXML
    void ritornaAlLogin(ActionEvent event) {
        Stage stage = (Stage) emailLabel.getScene().getWindow();;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/eu/bookcycle/gui/LoginView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

