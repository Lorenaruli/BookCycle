package it.uniroma2.eu.bookcycle.controller.gui2;

import it.uniroma2.eu.bookcycle.bean.Proposta3Bean;
import it.uniroma2.eu.bookcycle.controller.SceneManager;
import it.uniroma2.eu.bookcycle.controller.gui.GraphicController;
import it.uniroma2.eu.bookcycle.model.Eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.domain.StatoProposta;
import it.uniroma2.eu.bookcycle.controller.GestisciPropostaController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.swing.*;

public class AccettaRifiuta2ViewController extends GraphicController {

    @FXML
    private Label dettagliProposta;

    private long idProposta;
    private GestisciPropostaController controller;

    public void setDati(long idProposta, String titoloOfferto, String titoloRichiesto) {
        this.idProposta = idProposta;
        dettagliProposta.setText(titoloOfferto + " per " + titoloRichiesto);
    }


    public void setIdProposta(long id) {
        this.idProposta = id;
    }

    @FXML
    public void initialize() {
        try {
            controller = new GestisciPropostaController();
        } catch (PersistenzaException e) {
            showAlert("Errore tecnico. Riprovare più tardi.");
        }
    }

    @FXML
    void accettaProposta() {
        Proposta3Bean bean = new Proposta3Bean();
        bean.setIdProposta(idProposta);
        bean.setStato(StatoProposta.ACCETTATA);
        try {
            controller.gestisci(bean);
        } catch (PersistenzaException e) {
            showAlert("Errore tecnico. Riporvare più tardi.");
            return;
        } catch (OggettoInvalidoException e) {
            showAlert("Proposta non valida, riprovare.");
            return;
        }
    }

    @FXML
    void rifiutaProposta() {
        Proposta3Bean bean = new Proposta3Bean();
        bean.setIdProposta(idProposta);
        bean.setStato(StatoProposta.RIFIUTATA);
        try {
            controller.gestisci(bean);
        } catch (PersistenzaException e) {
            showAlert("Errore tecnico. Riporvare più tardi.");
            return;
        } catch (OggettoInvalidoException e) {
            showAlert("Proposta non valida, riprovare.");
            return;
        }
    }


    @FXML
    void annulla(ActionEvent event) {
        SceneManager.cambiaScena(event,"/it/uniroma2/eu/bookcycle/gui2/ProposteView.fxml");

    }
}



