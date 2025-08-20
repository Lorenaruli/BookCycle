
package it.uniroma2.eu.bookcycle.controller.gui;

import it.uniroma2.eu.bookcycle.controller.LoginController;
import it.uniroma2.eu.bookcycle.controller.SceneManager;
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

public class ProfiloViewController extends GraphicController{

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
    void Logout(ActionEvent event) {
        LoginController loginController = new LoginController();
        loginController.logout();
        SceneManager.cambiaScena(event,"/it/uniroma2/eu/bookcycle/gui/RegistrazioneView.fxml");

    }

    @FXML
    void caricaLibro(ActionEvent event) {
        SceneManager.cambiaScena(event,"/it/uniroma2/eu/bookcycle/gui/CaricaLibroView.fxml");




    }

    @FXML
    void cercaLibri(ActionEvent event) {
        SceneManager.cambiaScena(event,"/it/uniroma2/eu/bookcycle/gui/VediLibriTuttiView.fxml");



    }


    @FXML
    void gestisciProposte(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/eu/bookcycle/gui/GestisciProposteView.fxml"));
            Parent root = loader.load();
            GestisciProposteViewController ctrl = loader.getController();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Impossibile caricare la schermata del profilo.");
            e.printStackTrace();
        }
    }

    @FXML
    void vediLibriMiei (ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/eu/bookcycle/gui/LibriMieiView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void vediProposte(ActionEvent event) {
        SceneManager.cambiaScena(event,"/it/uniroma2/eu/bookcycle/gui/VediProposteInviateView.fxml");


    }


    }








