package it.uniroma2.eu.bookcycle.controller.gui;

import it.uniroma2.eu.bookcycle.bean.CaricaAnnuncioBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class InserisciPeriodoViewController {

    @FXML
    private ComboBox<String> durataCombo;

    private String scelta;

    @FXML
    public void initialize() {
        durataCombo.getItems().addAll("1 mese", "2 mesi", "3 mesi");
        durataCombo.setValue("1 mese");
    }

    @FXML
    void confermaDurata(ActionEvent event) {
        String scelta = durataCombo.getValue();
    }
    public int restituisciIntero(String string) {
        String numeroStr = string.split(" ")[0];

        try {
            return Integer.parseInt(numeroStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato non valido: " + string, e);
        }
    }

        public void beanModificato(CaricaAnnuncioBean bean){
            bean.setDurata(restituisciIntero(scelta));
        }

    }





