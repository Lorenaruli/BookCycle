package it.uniroma2.eu.bookcycle.controller.grafica.guicomune;

import it.uniroma2.eu.bookcycle.bean.CaricaLibroBean;
import it.uniroma2.eu.bookcycle.controller.applicativo.CaricaLibroController;
import it.uniroma2.eu.bookcycle.model.eccezioni.*;

import javafx.scene.control.TextField;

public abstract class CaricaLibroGui extends GraphicController {

    protected void carica( TextField titoloField, TextField autoreField, TextField genereField) {
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
            caricaLibroController.aggiungiLibro(caricaLibroBean);
        } catch (BeanInvalidoException _) {
            showAlert("Devi completare tutti i campi");
        } catch (OggettoInvalidoException _) {
           showAlert("Libro non valido: ");


        } catch (PersistenzaException _) {
            showAlert("Errore tecnico durante il salvataggio del libro. Riprova.");
        }catch (ClienteNonLoggatoException _){
            showAlert("Devi Prima loggarti.");
            goToLogin();
        }catch(RuoloClienteException _){
            showAlert("Cliente con ruolo sbagliato. Rieffettuare il login");
        }
        showAlert("Libro caricato con successo");

    }

    protected abstract void goToLogin();
}
