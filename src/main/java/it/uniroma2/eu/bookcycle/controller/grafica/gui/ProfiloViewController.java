
package it.uniroma2.eu.bookcycle.controller.grafica.gui;

import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.SceneManager;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.LogoutGui;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.ViewPath;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfiloViewController extends LogoutGui {

    @FXML
    private Button caricaLibriButton;

    @FXML
    private Button vediProposteInviate;

    @FXML
    private Button vediLibriMiei;

    @FXML
    private Button cercaLibriButton;

    @FXML
    private Label emailLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Button gestisciProposteButton;


    @FXML
    private Label usernameLabel;

    @FXML
    void logout(ActionEvent event) {
        String path= ViewPath.REGISTRAZIONE_VIEW;
        try {
            logoutCliente(event,path);
        } catch (PersistenzaException _) {
            showAlert("Errore tecnico. Riprovare più tardi.");
        }

    }

    @FXML
    void caricaLibro(ActionEvent event) {
        SceneManager.cambiaScena(event,ViewPath.CARICA_LIBRO_VIEW);




    }

    @FXML
    void cercaLibri(ActionEvent event) {
        SceneManager.cambiaScena(event,ViewPath.VEDI_LIBRI_TUTTI_VIEW);



    }


    @FXML
    void gestisciProposte(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ViewPath.GESTISCI_PROPOSTE_VIEW));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException _) {
            showAlert("Impossibile caricare la schermata del profilo.");
        }
    }

    @FXML
    void vediLibriMiei (ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ViewPath.LIBRI_MIEI_VIEW));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException _) {
           showAlert("Impossibile caricare la schermata.");
        }

    }
    @FXML
    void vediProposte(ActionEvent event) {
        SceneManager.cambiaScena(event,ViewPath.VEDI_PROPOSTE_INVIATE_VIEW);


    }


    }








