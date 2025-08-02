package it.uniroma2.eu.bookcycle.grafica.gui;

import javafx.scene.control.Alert;

public abstract class GraphicController {
    public void showAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(msg);
        alert.showAndWait();
    }
}
