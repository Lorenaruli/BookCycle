package it.uniroma2.eu.bookcycle.controller.gui;

import it.uniroma2.eu.bookcycle.bean.Proposta3Bean;
import it.uniroma2.eu.bookcycle.controller.GestisciPropostaController;
import it.uniroma2.eu.bookcycle.controller.SceneManager;
import it.uniroma2.eu.bookcycle.model.Eccezioni.OggettoInvalidoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.domain.StatoProposta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AccettaRifiutaViewController extends GraphicController{

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
        SceneManager.cambiaScena(event, "/it/uniroma2/eu/bookcycle/gui/ProfiloUtenteView.fxml");
    }

    @FXML
    private void rifiuta(ActionEvent e) {
        invia(StatoProposta.RIFIUTATA);
        showAlert("Proposta rifiutata");
        SceneManager.cambiaScena(e, "/it/uniroma2/eu/bookcycle/gui/ProfiloUtenteView.fxml");
    }


    private void invia(StatoProposta stato) {
        Proposta3Bean bean = new Proposta3Bean();
        bean.setIdProposta(idProposta);
        bean.setStato(stato);

        try {
            app= new GestisciPropostaController();
            app.gestisci(bean);
        } catch (PersistenzaException e) {
            showAlert("Errore tecnico, riprovare più tardi.");
            return;
        } catch (OggettoInvalidoException e) {
            showAlert("Proposta non trovata, riprovare.");
            return;
        }
    }

    @FXML
    private void tornaIndietro(ActionEvent e) {
        SceneManager.cambiaScena(e,"/it/uniroma2/eu/bookcycle/gui/GestisciProposteView.fxml");
    }



}

