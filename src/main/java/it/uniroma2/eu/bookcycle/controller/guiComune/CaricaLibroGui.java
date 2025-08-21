package it.uniroma2.eu.bookcycle.controller.guiComune;

import it.uniroma2.eu.bookcycle.bean.CaricaLibroBean;
import it.uniroma2.eu.bookcycle.controller.CaricaLibroController;
import it.uniroma2.eu.bookcycle.controller.gui.GraphicController;
import it.uniroma2.eu.bookcycle.model.Eccezioni.BeanInvalidoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.ClienteNonLoggatoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import javafx.event.ActionEvent;

import javafx.scene.control.TextField;

public abstract class CaricaLibroGui extends GraphicController {

    protected void carica(ActionEvent event, TextField titoloField, TextField autoreField, TextField genereField) {
        if (titoloField.getText().isBlank() ||
                autoreField.getText().isBlank() ||
                genereField.getText().isBlank()) {
            showAlert("Compila tutti i campi prima di caricare il libro");
            return;

        }
        try {
            CaricaLibroBean caricaLibroBean=new CaricaLibroBean();
            caricaLibroBean.setTitolo(titoloField.getText());
            caricaLibroBean.setAutore(autoreField.getText());
            caricaLibroBean.setGenere(genereField.getText());
            CaricaLibroController caricaLibroController = new CaricaLibroController();
            caricaLibroController.AggiungiLibro(caricaLibroBean);
        } catch (BeanInvalidoException e) {
            showAlert("Devi completare tutti i campi");
        } catch (OggettoInvalidoException e) {
            showAlert("Libro non valido: ");
        } catch (PersistenzaException e) {
            showAlert("Errore tecnico durante il salvataggio del libro. Riprova.");
        }catch (ClienteNonLoggatoException e){
            showAlert("Devi Prima loggarti.");
            goToLogin();

        }
        showAlert("Libro caricato con successo");

    }

    protected abstract void goToLogin();
}
