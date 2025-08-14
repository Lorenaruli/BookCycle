package it.uniroma2.eu.bookcycle.controller.gui;
import it.uniroma2.eu.bookcycle.bean.CaricaLibroBean;
import it.uniroma2.eu.bookcycle.controller.CaricaLibroController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class CaricaLibroViewController extends GraphicController {


        @FXML
        private TextField autoreField;

        @FXML
        private Button caricaButton;

        @FXML
        private TextField genereField;

        @FXML
        private TextField titoloField;

        @FXML
        private Button tornaIndietroButton;

        @FXML
        void caricaLibro(ActionEvent event) {
                CaricaLibroBean caricaLibroBean=new CaricaLibroBean();
                caricaLibroBean.setTitolo(titoloField.getText());
                caricaLibroBean.setAutore(autoreField.getText());
                caricaLibroBean.setGenere(genereField.getText());
                CaricaLibroController caricaLibroController = new CaricaLibroController();
                caricaLibroController.AggiungiLibro(caricaLibroBean);
            showAlert("Libro caricato con successo");

        }
        @FXML
        void tornaIndietro(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/eu/bookcycle/gui/ProfiloView.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }



        }

    }
