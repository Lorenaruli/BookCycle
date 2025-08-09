package it.uniroma2.eu.bookcycle.controller.gui;

import it.uniroma2.eu.bookcycle.bean.RegistrazioneBean;
import it.uniroma2.eu.bookcycle.controller.BeanInvalidoException;
import it.uniroma2.eu.bookcycle.controller.RegistrazioneController;
import it.uniroma2.eu.bookcycle.model.domain.Cliente;
import it.uniroma2.eu.bookcycle.model.domain.RuoloCliente;
import it.uniroma2.eu.bookcycle.model.domain.Utente;
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
        // Verifica campi vuoti PRIMA di creare il bean
        if (usernameLabel.getText().isBlank() ||
                passwordField.getText().isBlank() ||
                emailLabel.getText().isBlank() ||
                telephoneLabel.getText().isBlank()) {

            showAlert("Per favore, compila tutti i campi.");
            return;
        }

        // Se tutti i campi sono compilati, prosegui
        RegistrazioneBean registrazioneBean = new RegistrazioneBean();
        registrazioneBean.setUsername(usernameLabel.getText());
        registrazioneBean.setPassword(passwordField.getText());
        registrazioneBean.setEmail(emailLabel.getText());
        registrazioneBean.setTelefono(telephoneLabel.getText());
        registrazioneBean.setRuolo(libraioCheck.isSelected() ? LIBRAIO : UTENTE);

        RegistrazioneController registrazioneController = new RegistrazioneController();

        try {
            Cliente cliente = registrazioneController.registra(registrazioneBean);
            RuoloCliente ruoloCliente = (cliente instanceof Utente) ? UTENTE : LIBRAIO;
            showAlert("Registrazione avvenuta");

            switch (ruoloCliente) {
                case UTENTE -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/eu/bookcycle/gui/ProfiloView.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                }
                case LIBRAIO -> showAlert("La sezione per i librai non è disponibile.");
                default -> showAlert("Ruolo non riconosciuto.");
            }

        } catch (BeanInvalidoException e) {
            showAlert("Errore nel caricamento della schermata");
        } catch (IOException e) {
            showAlert("Errore nel caricamento della schermata");
            e.printStackTrace();
        }

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

