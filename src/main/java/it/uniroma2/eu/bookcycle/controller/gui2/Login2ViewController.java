package it.uniroma2.eu.bookcycle.controller.gui2;



import it.uniroma2.eu.bookcycle.bean2.SchermataAccessoBean;
import it.uniroma2.eu.bookcycle.controller.guiComune.LoginClienteGui;
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

public class Login2ViewController extends LoginClienteGui {

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registratiButton;

    @FXML
    private TextField usernameLabel;

    @FXML
    void login(ActionEvent event) {
        String path="/it/uniroma2/eu/bookcycle/gui2/Profilo2View.fxml";
        loginCliente(event, usernameLabel, passwordField,path);
    }

    @FXML
    void tornaARegistrazione(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/it/uniroma2/eu/bookcycle/gui2/Registrazione2View.fxml"));
            Parent root = loader.load();
            Registrazione2ViewController contr = loader.getController();
            SchermataAccessoBean bean = new SchermataAccessoBean();
            bean.setRuolo(RuoloCliente.UTENTE);
            contr.getRuoloSelezionato(bean);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Errore nel caricamento della schermata registrazione.");
            e.printStackTrace();
        }

    }

    @Override
    protected void goToLibraio() {
        showAlert("La sezione per i librai non è ancora disponibile");
    }
}

