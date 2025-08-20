package it.uniroma2.eu.bookcycle.controller.gui;

import javafx.scene.control.Alert;

public abstract class GraphicController {
    public void showAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}
