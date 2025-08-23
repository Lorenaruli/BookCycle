package it.uniroma2.eu.bookcycle.controller.grafica.gui;

import it.uniroma2.eu.bookcycle.bean.Proposta3Bean;
import it.uniroma2.eu.bookcycle.controller.applicativo.GestisciPropostaController;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.GraphicController;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.SceneManager;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.ViewPath;
import it.uniroma2.eu.bookcycle.model.eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.domain.StatoProposta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AccettaRifiutaViewController extends GraphicController {

    @FXML
    private Button accettaButton;

    @FXML
    private Button backButton;

    @FXML
    private Button rifiutaButton;

    private  GestisciPropostaController app;

    private long idProposta;

    public void setIdProposta(long id) {
        this.idProposta = id;
    }
    @FXML
    private void accetta(ActionEvent event) {
        invia(StatoProposta.ACCETTATA);
        showAlert("Proposta accettata");
        SceneManager.cambiaScena(event, ViewPath.PROFILO_UTENTE_VIEW);
    }

    @FXML
    private void rifiuta(ActionEvent e) {
        invia(StatoProposta.RIFIUTATA);
        showAlert("Proposta rifiutata");
        SceneManager.cambiaScena(e, ViewPath.PROFILO_UTENTE_VIEW);
    }


    private void invia(StatoProposta stato) {
        Proposta3Bean bean = new Proposta3Bean();
        bean.setIdProposta(idProposta);
        bean.setStato(stato);

        try {
            app= new GestisciPropostaController();
            app.gestisci(bean);
        } catch (PersistenzaException _) {
            showAlert("Errore tecnico, riprovare più tardi.");
        } catch (OggettoInvalidoException _) {
            showAlert("Proposta non trovata, riprovare.");
        }
    }

    @FXML
    private void tornaIndietro(ActionEvent e) {
        SceneManager.cambiaScena(e,ViewPath.GESTISCI_PROPOSTE_VIEW);
    }



}

