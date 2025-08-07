package it.uniroma2.eu.bookcycle.grafica.gui;


import it.uniroma2.eu.bookcycle.bean.CaricaLibroBean;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DettagliLibroViewController {//credo non servs

    @FXML
    private Label autoreLabel;

    @FXML
    private Label genereLabel;

    @FXML
    private Label titoloLabel;
    public void setLibro(CaricaLibroBean libro) {
        titoloLabel.setText(libro.getTitolo());
        autoreLabel.setText(libro.getAutore());
        genereLabel.setText(libro.getGenere());
    }

}

