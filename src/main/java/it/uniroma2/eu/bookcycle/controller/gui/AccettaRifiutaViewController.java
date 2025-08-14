package it.uniroma2.eu.bookcycle.controller.gui;

import it.uniroma2.eu.bookcycle.bean.Proposta3Bean;
import it.uniroma2.eu.bookcycle.controller.GestisciPropostaController;
import it.uniroma2.eu.bookcycle.model.domain.StatoProposta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AccettaRifiutaViewController extends GraphicController{

    @FXML
    private Button accettaButton;

    @FXML
    private Button backButton;

    @FXML
    private Button rifiutaButton;

    private final GestisciPropostaController app = new GestisciPropostaController();

    private long idProposta;

    public void setIdProposta(long id) {
        this.idProposta = id;
    }
    @FXML
    private void accetta(ActionEvent event) {
        invia(StatoProposta.ACCETTATA);
        showAlert("Proposta accettata");
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/it/uniroma2/eu/bookcycle/gui/ProfiloView.fxml")
            );
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException ex) {
            showAlert("Impossibile caricare la schermata del profilo.");
            ex.printStackTrace();
        }



    }

    @FXML
    private void rifiuta(ActionEvent e) {
        invia(StatoProposta.RIFIUTATA);
        showAlert("Proposta rifiutata");
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/it/uniroma2/eu/bookcycle/gui/ProfiloView.fxml")
            );
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException ex) {
            showAlert("Impossibile caricare la schermata del profilo.");
            ex.printStackTrace();
        }
    }


    private void invia(StatoProposta stato) {
        Proposta3Bean bean = new Proposta3Bean();
        bean.setIdProposta(idProposta);
        bean.setStato(stato);

        app.gestisci(bean);
    }

    @FXML
    private void tornaIndietro(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/eu/bookcycle/gui/GestisciProposteView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException event) {
            throw new RuntimeException(event);
        }
    }



}

