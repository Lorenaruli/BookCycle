package it.uniroma2.eu.bookcycle.controller.gui;
import it.uniroma2.eu.bookcycle.bean.CaricaLibroBean;
import it.uniroma2.eu.bookcycle.controller.CaricaLibroController;
import it.uniroma2.eu.bookcycle.controller.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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

            if (titoloField.getText().isBlank() ||
                    autoreField.getText().isBlank() ||
                    genereField.getText().isBlank()) {

                showAlert("Compila tutti i campi prima di caricare il libro");
                return;
            }

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
            SceneManager.cambiaScena(event,"/it/uniroma2/eu/bookcycle/gui/ProfiloView.fxml");
        }

    }
