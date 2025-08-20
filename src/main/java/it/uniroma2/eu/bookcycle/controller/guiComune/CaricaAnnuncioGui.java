package it.uniroma2.eu.bookcycle.controller.guiComune;

import it.uniroma2.eu.bookcycle.bean.CaricaAnnuncioBean;
import it.uniroma2.eu.bookcycle.bean.CaricaLibroBean;
import it.uniroma2.eu.bookcycle.controller.CaricaAnnuncioController;
import it.uniroma2.eu.bookcycle.controller.CaricaLibroController;
import it.uniroma2.eu.bookcycle.controller.gui.GraphicController;
import it.uniroma2.eu.bookcycle.model.domain.TipoAnnuncio;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import static it.uniroma2.eu.bookcycle.model.domain.RuoloCliente.LIBRAIO;
import static it.uniroma2.eu.bookcycle.model.domain.RuoloCliente.UTENTE;
import static it.uniroma2.eu.bookcycle.model.domain.TipoAnnuncio.ANNUNCIONOLEGGIO;
import static it.uniroma2.eu.bookcycle.model.domain.TipoAnnuncio.ANNUNCIOVENDITA;

public abstract class CaricaAnnuncioGui extends GraphicController {
    protected void aggiungi(ActionEvent event, TextField titoloField, TextField autoreField, TextField prezzoField, CheckBox check) {
        if (titoloField.getText().isBlank() ||
                autoreField.getText().isBlank() ||
                prezzoField.getText().isBlank()) {
            showAlert("Compila tutti i campi prima di caricare l'annuncio");
            return;
        }

        Double prezzo = ottieniPrezzo(prezzoField);
        if (prezzo == null || prezzo < 0) {
            showAlert("Inserisci un prezzo valido");
            return;
        }


        CaricaAnnuncioBean caricaAnnuncioBean=new CaricaAnnuncioBean();
        caricaAnnuncioBean.setTitolo(titoloField.getText());
        caricaAnnuncioBean.setAutore(autoreField.getText());
        caricaAnnuncioBean.setPrezzo(prezzo);
        setTipo(caricaAnnuncioBean);

        if(check.isSelected()){
            goToNoleggio(caricaAnnuncioBean);
            return;}

        CaricaAnnuncioController caricaAnnuncioController = new CaricaAnnuncioController();
        caricaAnnuncioController.AggiungiAnnuncio(caricaAnnuncioBean);
        showAlert("Annuncio caricato con successo");

    }

    public abstract void setTipo(CaricaAnnuncioBean bean);
    public abstract void goToNoleggio(CaricaAnnuncioBean bean);





    private Double ottieniPrezzo(TextField prezzo) {
        String s = prezzo.getText();
        if (s == null) return null;

        try {
            double value = Double.parseDouble(s);
            return value;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
