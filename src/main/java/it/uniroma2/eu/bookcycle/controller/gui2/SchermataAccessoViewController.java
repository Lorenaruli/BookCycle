package it.uniroma2.eu.bookcycle.controller.gui2;
import it.uniroma2.eu.bookcycle.bean2.SchermataAccessoBean;
import it.uniroma2.eu.bookcycle.controller.gui.GraphicController;
import it.uniroma2.eu.bookcycle.model.domain.RuoloCliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SchermataAccessoViewController extends GraphicController {

    @FXML
    private Button libraioButton;

    @FXML
    private Button utenteButton;

    SchermataAccessoBean bean;

    @FXML
    void accessoLibraio(ActionEvent event){
        bean=new SchermataAccessoBean();
        bean.setRuolo(RuoloCliente.LIBRAIO);
        apriRegistrazione(event, RuoloCliente.LIBRAIO);


    }

    @FXML
    void accessoUtente(ActionEvent event) {
        bean = new SchermataAccessoBean();
        bean.setRuolo(RuoloCliente.UTENTE);
        apriRegistrazione(event, RuoloCliente.UTENTE);
    }

    private void apriRegistrazione(ActionEvent event, RuoloCliente ruolo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/eu/bookcycle/gui/Registrazione2View.fxml"));
            Parent root = loader.load();
            Registrazione2ViewController regCtrl = loader.getController();
            regCtrl.getRuoloSelezionato(bean);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    }



