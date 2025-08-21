package it.uniroma2.eu.bookcycle.controller.gui2;
import it.uniroma2.eu.bookcycle.bean.RegistrazioneBean;
import it.uniroma2.eu.bookcycle.bean.SchermataAccessoBean;
import it.uniroma2.eu.bookcycle.controller.SceneManager;

import it.uniroma2.eu.bookcycle.controller.guiComune.RegistraClienteGui;
import it.uniroma2.eu.bookcycle.model.domain.RuoloCliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class Registrazione2ViewController extends RegistraClienteGui {

    @FXML
    private Button PrecedentRegistratoButton;

    @FXML
    private Button RegistratiButton;

    @FXML
    private TextField emailLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField telephoneLabel;

    @FXML
    private TextField usernameLabel;

    private RuoloCliente ruolo;

    public void getRuoloSelezionato(SchermataAccessoBean bean){
        this.ruolo=bean.getRuolo();
    }


    @FXML
    void registra(ActionEvent event) {
            String path="/it/uniroma2/eu/bookcycle/gui2/Profilo2View.fxml";

            registraCliente(event, usernameLabel, passwordField, emailLabel, telephoneLabel, path );
        }

        @Override
        public void setRuolo(RegistrazioneBean registrazioneBean){
            registrazioneBean.setRuolo(ruolo);
        }

        @Override
        public void goToLibraio(){
        showAlert("La sezione per i librai non è ancora disponibile");
        }




    @FXML
    void ritornaAlLogin(ActionEvent event) {
        SceneManager.cambiaScena(event,"/it/uniroma2/eu/bookcycle/gui2/Login2View.fxml");

        }
        }







