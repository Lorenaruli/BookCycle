package it.uniroma2.eu.bookcycle.controller.guiComune;

import it.uniroma2.eu.bookcycle.bean.CaricaLibroBean;
import it.uniroma2.eu.bookcycle.controller.CaricaLibroController;
import it.uniroma2.eu.bookcycle.controller.gui.GraphicController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public abstract class CaricaLibroGui extends GraphicController {

    protected void carica(ActionEvent event, TextField titoloField, TextField autoreField, TextField genereField) {
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
}
