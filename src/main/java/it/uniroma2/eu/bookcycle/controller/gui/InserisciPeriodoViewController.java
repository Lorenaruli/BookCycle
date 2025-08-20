package it.uniroma2.eu.bookcycle.controller.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class InserisciPeriodoViewController {

    @FXML
    private ComboBox<String> durataCombo;

    @FXML
    public void initialize() {
        durataCombo.getItems().addAll("1 mese", "2 mesi", "3 mesi");
        durataCombo.setValue("1 mese");
    }

    @FXML
    void confermaDurata(ActionEvent event) {
        String scelta = durataCombo.getValue();
        System.out.println("Hai scelto: " + scelta);
    }



}
