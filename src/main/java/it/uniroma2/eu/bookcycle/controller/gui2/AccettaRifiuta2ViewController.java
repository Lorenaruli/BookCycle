package it.uniroma2.eu.bookcycle.controller.gui2;

import it.uniroma2.eu.bookcycle.bean.Proposta3Bean;
import it.uniroma2.eu.bookcycle.model.domain.StatoProposta;
import it.uniroma2.eu.bookcycle.controller.GestisciPropostaController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AccettaRifiuta2ViewController {

    @FXML
    private Label dettagliProposta;

    private long idProposta;
    private GestisciPropostaController controller = new GestisciPropostaController();

    public void setDati(long idProposta, String titoloOfferto, String titoloRichiesto) {
        this.idProposta = idProposta;
        dettagliProposta.setText(titoloOfferto + " per " + titoloRichiesto);
    }


    public void setIdProposta(long id) {
        this.idProposta = id;
    }

    @FXML
    void accettaProposta() {
        Proposta3Bean bean = new Proposta3Bean();
        bean.setIdProposta(idProposta);
        bean.setStato(StatoProposta.ACCETTATA);
        controller.gestisci(bean);
    }

    @FXML
    void rifiutaProposta() {
        Proposta3Bean bean = new Proposta3Bean();
        bean.setIdProposta(idProposta);
        bean.setStato(StatoProposta.RIFIUTATA);
        controller.gestisci(bean);
    }


    @FXML
    void annulla(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/eu/bookcycle/gui2/ProposteView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }



