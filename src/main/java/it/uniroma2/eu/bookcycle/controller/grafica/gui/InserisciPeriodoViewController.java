package it.uniroma2.eu.bookcycle.controller.grafica.gui;

import it.uniroma2.eu.bookcycle.bean.CaricaAnnuncioBean;
import it.uniroma2.eu.bookcycle.controller.applicativo.CaricaAnnuncioController;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.GraphicController;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.SceneManager;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.ViewPath;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class InserisciPeriodoViewController extends GraphicController {

    @FXML
    private ComboBox<String> durataCombo;

    private String scelta;
    private CaricaAnnuncioBean bean;

    public void setBean(CaricaAnnuncioBean bean) {
        this.bean = bean;
    }

    @FXML
    public void initialize() {
        durataCombo.getItems().addAll("1 mese", "2 mesi", "3 mesi");
        durataCombo.setValue("1 mese");
    }

    @FXML
    void confermaDurata(ActionEvent event) {

        String scelta = durataCombo.getValue();
        if (scelta == null || scelta.isBlank()) {
            showAlert("Seleziona una durata valida");
            return;
        }
        int durata = restituisciIntero(scelta);
        bean.setDurata(durata);


        try {
            CaricaAnnuncioController caricaAnnuncioController = new CaricaAnnuncioController();
            caricaAnnuncioController.aggiungiAnnuncio(bean);

            showAlert("Annuncio di noleggio caricato con successo.");
            SceneManager.cambiaScena(event, ViewPath.PROFILO_LIBRAIO_VIEW);
        } catch (Exception e) {
            showAlert("Errore durante il caricamento: " + e.getMessage());
        }
    }

    public int restituisciIntero(String string) {
        String numeroStr = string.split(" ")[0];

        try {
            return Integer.parseInt(numeroStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato non valido: " + string, e);
        }
    }

    public void beanModificato(CaricaAnnuncioBean bean) {
         scelta = durataCombo.getValue();

        if (scelta == null || scelta.isBlank()) {
            showAlert("Seleziona una durata prima di confermare.");
            return;
        }

        try {
            int durata = restituisciIntero(scelta);
            bean.setDurata(durata);
        } catch (IllegalArgumentException _) {
            showAlert("Durata non valida: " + scelta);
        }
    }
    }





