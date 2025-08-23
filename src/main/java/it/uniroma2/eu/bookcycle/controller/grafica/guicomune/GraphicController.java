package it.uniroma2.eu.bookcycle.controller.grafica.guicomune;

import javafx.scene.control.Alert;

public abstract class GraphicController {

    protected GraphicController() {
    }

    public static void showAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}
