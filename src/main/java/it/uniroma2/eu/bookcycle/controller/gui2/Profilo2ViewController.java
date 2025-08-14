package it.uniroma2.eu.bookcycle.controller.gui2;

import it.uniroma2.eu.bookcycle.controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Profilo2ViewController {

    @FXML
    private Button cercaLibriButton;

    @FXML
    private Button gestisciProposteButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button vediLibriMieiButton;

    @FXML
    void Logout(ActionEvent event) {
        LoginController loginController = new LoginController();
        loginController.logout();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/eu/bookcycle/gui/SchermataProfiloView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

        }


    @FXML
    void cercaLibri(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/eu/bookcycle/gui/TipoLibroView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void gestisciProposte(ActionEvent event) {

    }

    @FXML
    void vediLibriMiei(ActionEvent event) {

    }

}

