package it.uniroma2.eu.bookcycle.controller.gui2;

import it.uniroma2.eu.bookcycle.bean.Proposta3Bean;
import it.uniroma2.eu.bookcycle.model.domain.StatoProposta;
import it.uniroma2.eu.bookcycle.controller.GestisciPropostaController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AccettaRifiuta2ViewController {

    @FXML
    private Label dettagliProposta;

    private long idProposta;
    private GestisciPropostaController controller = new GestisciPropostaController();

    public void setDati(long idProposta, String titoloOfferto, String titoloRichiesto) {
        this.idProposta = idProposta;
        dettagliProposta.setText(titoloOfferto + " per " + titoloRichiesto);
    }

    @FXML
    void accettaProposta() {
        Proposta3Bean bean = new Proposta3Bean();
        bean.setIdProposta(idProposta);
        bean.setStato(StatoProposta.ACCETTATA);
        controller.gestisci(bean);
        chiudi();
    }

    @FXML
    void rifiutaProposta() {
        Proposta3Bean bean = new Proposta3Bean();
        bean.setIdProposta(idProposta);
        bean.setStato(StatoProposta.RIFIUTATA);
        controller.gestisci(bean);
        chiudi();
    }

    @FXML
    void annulla() {
        chiudi();
    }

    private void chiudi() {
        Stage stage = (Stage) dettagliProposta.getScene().getWindow();
        stage.close();
    }
}
