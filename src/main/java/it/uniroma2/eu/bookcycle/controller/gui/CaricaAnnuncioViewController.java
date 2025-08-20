package it.uniroma2.eu.bookcycle.controller.gui;

import it.uniroma2.eu.bookcycle.bean.CaricaAnnuncioBean;
import it.uniroma2.eu.bookcycle.controller.SceneManager;
import it.uniroma2.eu.bookcycle.controller.guiComune.CaricaAnnuncioGui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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
        aggiungi(event, titoloField, autoreField,prezzoField);
        if(checkButton.isSelected()){
            SceneManager.cambiaScena(event,"/it/uniroma2/eu/bookcycle/controller/gui/InserisciPeriodoViewController.fxml");
        }


    }

    @Override
    public void setTipo(CaricaAnnuncioBean bean) {
        bean.setTipo(checkButton.isSelected() ? ANNUNCIONOLEGGIO : ANNUNCIOVENDITA);


    }

    @FXML
    void tornaIndietro(ActionEvent event) {
        SceneManager.cambiaScena(event,"/it/uniroma2/eu/bookcycle/gui/ProfiloLibraioView.fxml");

    }

}

