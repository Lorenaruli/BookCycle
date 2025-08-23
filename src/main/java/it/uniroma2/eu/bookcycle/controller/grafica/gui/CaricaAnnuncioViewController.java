package it.uniroma2.eu.bookcycle.controller.grafica.gui;

import it.uniroma2.eu.bookcycle.bean.CaricaAnnuncioBean;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.SceneManager;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.CaricaAnnuncioGui;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.ViewPath;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static it.uniroma2.eu.bookcycle.model.domain.TipoAnnuncio.ANNUNCIONOLEGGIO;
import static it.uniroma2.eu.bookcycle.model.domain.TipoAnnuncio.ANNUNCIOVENDITA;

public class CaricaAnnuncioViewController extends CaricaAnnuncioGui {

    @FXML
    private TextField autoreField;

    @FXML
    private Button caricaButton;

    @FXML
    private Button indietroButton;

    @FXML
    private TextField prezzoField;

    @FXML
    private TextField titoloField;

    @FXML
    private CheckBox checkButton;

    @FXML
    void caricaAnnuncio(ActionEvent event) {

        aggiungi( titoloField, autoreField,prezzoField, checkButton);
    }

    @Override
    public void goToNoleggio(CaricaAnnuncioBean bean){
        try {
            String path = ViewPath.INSERISCI_PERIODO_VIEW;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            InserisciPeriodoViewController controller = loader.getController();
            controller.setBean(bean);
            Stage stage = (Stage) checkButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException _) {
            showAlert("Impossibile caricare la schermata.");
        }

    }

    @Override
    public void setTipo(CaricaAnnuncioBean bean) {
        bean.setTipo(checkButton.isSelected() ? ANNUNCIONOLEGGIO : ANNUNCIOVENDITA);


    }

    @FXML
    void tornaIndietro(ActionEvent event) {
        SceneManager.cambiaScena(event,ViewPath.PROFILO_LIBRAIO_VIEW);

    }

}

