package it.uniroma2.eu.bookcycle.controller.guiComune;

import it.uniroma2.eu.bookcycle.bean.CaricaAnnuncioBean;
import it.uniroma2.eu.bookcycle.controller.CaricaAnnuncioController;
import it.uniroma2.eu.bookcycle.controller.gui.GraphicController;
import it.uniroma2.eu.bookcycle.model.Eccezioni.ClienteNonLoggatoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.RuoloClienteException;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;


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
            return;
        }

        CaricaAnnuncioController caricaAnnuncioController=null;
        try {
            caricaAnnuncioController = new CaricaAnnuncioController();
        } catch (ClienteNonLoggatoException e) {
            showAlert("Devi prima loggarti");
        }
        try {
            caricaAnnuncioController.AggiungiAnnuncio(caricaAnnuncioBean);
        } catch (PersistenzaException e) {
            showAlert("Errore tecnico: impossibile accedere ai dati. Riprova più tardi.");
        } catch (RuoloClienteException e) {
            showAlert("Ruolo non valido per questa operazione.");
        } catch (OggettoInvalidoException e) {
            showAlert("Dati non validi: verifica i campi inseriti.");
        }

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
