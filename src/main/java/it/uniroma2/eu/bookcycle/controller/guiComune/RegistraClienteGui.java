package it.uniroma2.eu.bookcycle.controller.guiComune;

import it.uniroma2.eu.bookcycle.bean.ClienteBean;
import it.uniroma2.eu.bookcycle.bean.RegistrazioneBean;
import it.uniroma2.eu.bookcycle.model.Eccezioni.BeanInvalidoException;
import it.uniroma2.eu.bookcycle.controller.RegistrazioneController;
import it.uniroma2.eu.bookcycle.controller.gui.GraphicController;
import it.uniroma2.eu.bookcycle.model.Eccezioni.GuiException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.OggettoEsistenteException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
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


    public void registraCliente(ActionEvent event, TextField usernameLabel, PasswordField passwordField, TextField emailLabel, TextField telephoneLabel, String path) {
        if (usernameLabel.getText().isBlank() ||
                passwordField.getText().isBlank() ||
                emailLabel.getText().isBlank() ||
                telephoneLabel.getText().isBlank()) {

            showAlert("Per favore, compila tutti i campi.");
            return;
        }

        RegistrazioneBean registrazioneBean = new RegistrazioneBean();
        registrazioneBean.setUsername(usernameLabel.getText());
        registrazioneBean.setPassword(passwordField.getText());
        registrazioneBean.setEmail(emailLabel.getText());
        registrazioneBean.setTelefono(telephoneLabel.getText());
        setRuolo(registrazioneBean);

        RegistrazioneController registrazioneController = new RegistrazioneController();

        try {
            ClienteBean clienteBean = registrazioneController.registra(registrazioneBean);
            RuoloCliente ruoloCliente = (clienteBean.getRuoloCliente())== UTENTE ? UTENTE : LIBRAIO;
            showAlert("Registrazione avvenuta");

            switch (ruoloCliente) {
                case UTENTE -> {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                }
               case LIBRAIO ->  {goToLibraio();}
            }

        } catch (BeanInvalidoException e) {
            showAlert("Ci sono dei campi vuoti");
        } catch (OggettoEsistenteException e) {
            showAlert("Errore: username già registrato, scegline un altro.");
        } catch (PersistenzaException e) {
            showAlert("Errore tecnico: impossibile salvare i dati, riprova più tardi.");
        } catch (IOException e) {
            throw new GuiException("Errore caricamento Schermata Profilo");
        }


    }
    protected abstract void setRuolo(RegistrazioneBean registrazioneBean);
    protected abstract void goToLibraio();
}
