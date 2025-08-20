package it.uniroma2.eu.bookcycle.controller.gui;

import it.uniroma2.eu.bookcycle.bean.ClienteBean;
import it.uniroma2.eu.bookcycle.bean.RegistrazioneBean;
import it.uniroma2.eu.bookcycle.controller.BeanInvalidoException;
import it.uniroma2.eu.bookcycle.controller.RegistrazioneController;
import it.uniroma2.eu.bookcycle.controller.SceneManager;
import it.uniroma2.eu.bookcycle.controller.guiComune.RegistraClienteGui;
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
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.io.IOException;

import static it.uniroma2.eu.bookcycle.model.domain.RuoloCliente.LIBRAIO;
import static it.uniroma2.eu.bookcycle.model.domain.RuoloCliente.UTENTE;

public class RegistrazioneViewController extends RegistraClienteGui {

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
        String path = "/it/uniroma2/eu/bookcycle/gui/ProfiloView.fxml";

        registraCliente(event, usernameLabel, passwordField, emailLabel, telephoneLabel, path);
    }

    @Override
    public void setRuolo(RegistrazioneBean registrazioneBean) {
        registrazioneBean.setRuolo(libraioCheck.isSelected() ? LIBRAIO : UTENTE);
    }



    @FXML
    void ritornaAlLogin(ActionEvent event) {
        SceneManager.cambiaScena(event,"/it/uniroma2/eu/bookcycle/gui/LoginView.fxml");
    }


}

