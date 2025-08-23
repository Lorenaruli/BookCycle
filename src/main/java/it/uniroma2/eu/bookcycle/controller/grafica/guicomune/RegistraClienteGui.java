package it.uniroma2.eu.bookcycle.controller.grafica.guicomune;

import it.uniroma2.eu.bookcycle.bean.ClienteBean;
import it.uniroma2.eu.bookcycle.bean.RegistrazioneBean;
import it.uniroma2.eu.bookcycle.model.eccezioni.BeanInvalidoException;
import it.uniroma2.eu.bookcycle.controller.applicativo.RegistrazioneController;
import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoEsistenteException;
import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
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

import static it.uniroma2.eu.bookcycle.model.domain.RuoloCliente.LIBRAIO;
import static it.uniroma2.eu.bookcycle.model.domain.RuoloCliente.UTENTE;

public abstract class RegistraClienteGui extends GraphicController {

    private  RegistrazioneController registrazioneController;

    protected RegistraClienteGui() {
        try {
            this.registrazioneController = new RegistrazioneController();
        } catch (PersistenzaException _) {
            showAlert("Errore tecnico. Riprovare più tardi");
        }
    }


    public void registraCliente(ActionEvent event, TextField usernameLabel, PasswordField passwordField, TextField emailLabel, TextField telephoneLabel, String path) {
        if (usernameLabel.getText().isBlank() ||
                passwordField.getText().isBlank() ||
                emailLabel.getText().isBlank() ||
                telephoneLabel.getText().isBlank()) {

            showAlert("Per favore, compila tutti i campi.");
            return;
        }

        RegistrazioneBean registrazioneBean = new RegistrazioneBean();
        try {
            registrazioneBean.setUsername(usernameLabel.getText());
        } catch (OggettoInvalidoException _) {
            showAlert("Username non valido. Riprovare");
            return;
        }
        registrazioneBean.setPassword(passwordField.getText());
        registrazioneBean.setEmail(emailLabel.getText());
        registrazioneBean.setTelefono(telephoneLabel.getText());
        setRuolo(registrazioneBean);



        try {
            ClienteBean clienteBean = registrazioneController.registra(registrazioneBean);
            RuoloCliente ruoloCliente = clienteBean.getRuoloCliente();
            showAlert("Registrazione avvenuta");

            if (ruoloCliente == UTENTE) {
                apriSchermata(path, event);
            } else if (ruoloCliente == LIBRAIO) {
                goToLibraio();
            }

        } catch (BeanInvalidoException _) {
            showAlert("Ci sono dei campi vuoti");
        } catch (OggettoEsistenteException _) {
            showAlert("Errore: username già registrato, scegline un altro.");
        } catch (PersistenzaException _) {
            showAlert("Errore tecnico: impossibile salvare i dati, riprova più tardi.");
       }


    }

    private void apriSchermata(String path, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException _) {
            showAlert("Errore caricamento schermata");
        }
    }
    protected abstract void setRuolo(RegistrazioneBean registrazioneBean);
    protected abstract void goToLibraio();
}
