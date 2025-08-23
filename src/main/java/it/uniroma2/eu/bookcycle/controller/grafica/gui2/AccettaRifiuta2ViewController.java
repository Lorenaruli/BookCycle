package it.uniroma2.eu.bookcycle.controller.grafica.gui2;

import it.uniroma2.eu.bookcycle.bean.Proposta3Bean;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.SceneManager;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.GraphicController;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.ViewPath;
import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.domain.StatoProposta;
import it.uniroma2.eu.bookcycle.controller.applicativo.GestisciPropostaController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
        } catch (PersistenzaException _) {
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
        } catch (PersistenzaException _) {
            showAlert("Errore tecnico. Riporvare più tardi.");
        } catch (OggettoInvalidoException _) {
            showAlert("Proposta non valida, riprovare.");
        }
    }

    @FXML
    void rifiutaProposta() {
        Proposta3Bean bean = new Proposta3Bean();
        bean.setIdProposta(idProposta);
        bean.setStato(StatoProposta.RIFIUTATA);
        try {
            controller.gestisci(bean);
        } catch (PersistenzaException _) {
            showAlert("Errore tecnico. Riporvare più tardi.");
        } catch (OggettoInvalidoException _) {
            showAlert("Proposta non valida, riprovare.");
        }
    }


    @FXML
    void annulla(ActionEvent event) {
        SceneManager.cambiaScena(event, ViewPath.PROPOSTE_VIEW);

    }
}



